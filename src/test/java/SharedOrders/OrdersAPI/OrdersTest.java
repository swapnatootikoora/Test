package SharedOrders.OrdersAPI;

import apirequests.APIRequests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.apache.http.HttpStatus;
import org.json.JSONArray;

import org.junit.Assert;
import org.junit.Test;
import utilities.Utilities;

import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class OrdersTest {

    String path = " https://gcotest.global.com/orders/api/Orders";
    private static String fileSep = System.getProperty("file.separator");


    /*This test is to create a new order using the API : POST*, when Client Brand & BrandProduct = NULL*/
    @Test
    public void createOrderTestHappyPath() {

        String filename = "clientBrandAndProductNull.json";
        String filePath = Utilities.buildFilePath("orders/",filename);
        String payload= Utilities.readFile(filePath);
        Response response = APIRequests.PostAPI(path,payload);
        Assert.assertEquals("Check status codes for succesful response  ", response.getStatusCode(), HttpStatus.SC_CREATED);
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String name =jsonPath.getString("name");
        Assert.assertEquals("The order name doesn't match","Orders Test",name);/*The Assert only gets executed when the expected and actual doesn't match*/
        System.out.println("Order name : \n" + jsonPath.getString("name"));
        System.out.println("Order id : \n" + jsonPath.getString("id"));
    }



/*This test is to verify that an order can be created when a valid client brand and brand product are provided*/
    @Test
    public void createOrderwithValidClientBrandandBrandProductTest() {

        String filename = "validClientBrandAndbrandProduct.json";
        String filePath = Utilities.buildFilePath("orders/",filename);
        String payload= Utilities.readFile(filePath);
        Response response = APIRequests.PostAPI(path,payload);
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("Order name : \n" + jsonPath.getString("name"));
        System.out.println("Order id : \n" + jsonPath.getString("id"));
        String responseBody = response.asString();
        System.out.println("The order details are :\n" + responseBody);
    }



 /* This test is to verify that an order can be created when a valid client brand and brand product is NULL*/

    @Test
    public void createOrderwithValidClientBrandandNullBrandProductTest() {

        String filename = "validClientBrandAndNullbrandProduct.json";
        String filePath = Utilities.buildFilePath("orders/",filename);
        String payload= Utilities.readFile(filePath);
        Response response = APIRequests.PostAPI(path,payload);
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("Order name : \n" + jsonPath.getString("name"));
        System.out.println("Order id : \n" + jsonPath.getString("id"));
        String responseBody = response.asString();
        System.out.println("The order details are :\n" + responseBody);
    }



    /*This test is to verify that an order can't be created when client brand is Null and brand product is specified*/
    @Test
    public void NullClientBrandTest() {
        String filename = "NullClientBrandAndvalidlbrandProduct.json";
        String filePath = Utilities.buildFilePath("orders/",filename);
        String payload= Utilities.readFile(filePath);
        Response response = APIRequests.PostAPI(path,payload);
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("Order name : \n" + jsonPath.getString("name"));
        System.out.println("Order id : \n" + jsonPath.getString("id"));
        String responseBody = response.asString();
        System.out.println("The order can't be created :\n" + responseBody);
    }




    /*This test is to verify that an Order is not created when both clientBrandId and brandProductId are valid and linked but clientBrandId is Disabled*/
    @Test
    public void disabledClientBrandTest() {
    String filename = "DisabledClientBrand.json";
    String filePath = Utilities.buildFilePath("orders/",filename);
    String payload= Utilities.readFile(filePath);
    Response response = APIRequests.PostAPI(path,payload);
    System.out.println(">>>>> " + response.getStatusCode());
    JsonPath jsonPath = new JsonPath(response.getBody().asString());
    System.out.println("Order name : \n" + jsonPath.getString("name"));
    System.out.println("Order id : \n" + jsonPath.getString("id"));
    String responseBody = response.asString();
    System.out.println("The order can't be created :\n" + responseBody);
}



    /*This test is to verify that an Order is not created when both clientBrandId and brandProductId are valid and not Disabled but are not linked*/
    @Test
    public void DisjointClientBrandAndBrandProductTest() {
        String filename = "DisjointClientbrandAndBrandproduct.json";
        String filePath = Utilities.buildFilePath("orders/",filename);
        String payload= Utilities.readFile(filePath);
        Response response = APIRequests.PostAPI(path,payload);
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("Order name : \n" + jsonPath.getString("name"));
        System.out.println("Order id : \n" + jsonPath.getString("id"));
        String responseBody = response.asString();
        System.out.println("The order can't be created :\n" + responseBody);
    }



    /*This test gets all orders and then fetches a particular order by its ID*/
    @Test
    public void getAllOrdersTest() {

        Response response = APIRequests.getAPI(path);
        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
        JSONArray jsonResponse = new JSONArray(response.asString());
        String id = jsonResponse.getJSONObject(0).getString("id");
        Response response1 = APIRequests.getAPI(path + fileSep + id);
        String response1Body = response1.asString();
        System.out.println("The order details are :\n" + response1Body);

    }


    /*This test returns a 404 :NOT FOUND message , when an order doesn't exists*/
    @Test
    public void InvalidOrderIdTest() {
        String orderID = "435ad8a0-0664-407b-ab8c-aa85009f94a8";
        Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(path + fileSep + orderID)
                .then().extract().response();

        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);

    }


}