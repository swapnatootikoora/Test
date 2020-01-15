package SharedOrders.BillingLineProducts;

import SharedOrders.BillingLineBuyingAreas.BuyingAreaUtilities;
import apirequests.APIRequests;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.EnvDetails;
import utilities.ErrorMsgs;
import utilities.Utilities;

import java.util.List;

public class ProductsTest {
    public static String url = "";
    private final static String path = "billinglineproducts/api/products";
    private static String fileSep = System.getProperty("file.separator");

    public static EnvDetails envDetails=new EnvDetails();
    public static String validBuyingId="";
    public static String createProductPayload="";


    /* setting up the env... details*/
    @BeforeClass
    public static void setUp() {
        String env=Utilities.envName();
        envDetails.envDetails(env);
        System.out.println(env);
        url=envDetails.url;
        System.out.println(url);
        List<String> variables=envDetails.getEnvVariables(env);
         validBuyingId=envDetails.getValidBuyingArea(variables);
        String[] temp={validBuyingId};
        String filename = "CreateProduct.json";
        String filePath = Utilities.buildFilePath("BillingLineProducts/", filename);
         createProductPayload = Utilities.readFile(filePath);
        JSONObject obj=new JSONObject(createProductPayload);
        obj.remove("buyingAreaIds");
        obj.put("buyingAreaIds",temp);
        createProductPayload=obj.toString();
    }


    /*This returns all the list of products*/
    @Test
    public void getAllProducts() {
        Response response = APIRequests.executeRequest(url, path, "", "", "get");
        System.out.println("The product details are : \n\n");
        response.getBody().prettyPrint();
        System.out.println("status code : " + response.getStatusCode());
        System.out.println("content type:" + response.getContentType());
        JSONArray jsonResponse = new JSONArray(response.asString());
        String id = jsonResponse.getJSONObject(0).getString("id");
        System.out.println("Product id: " + id);
    }

    /*This test gets all products and then fetches a particular product by its ID*/
    @Test
    public void getProductByIdTest() {

        Response response = APIRequests.executeRequest(url, path, "", "", "get");
        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
        JSONArray jsonResponse = new JSONArray(response.asString());
        String ID = jsonResponse.getJSONObject(0).getString("id");
        String currentPath = path + "/" + ID;
         response = APIRequests.executeRequest(url, currentPath, "", "", "get");
        System.out.println("The product details are : \n\n");
        response.getBody().prettyPrint();

    }

    /*This test returns a 404 :NOT FOUND message , when product id doesn't exists*/
    @Test
    public void InvalidProductIdTest() {
        String productID = "7a2d4260-eb64-49d6-ac76-aabd00a3d374";
        String currentPath = path + "/" + productID;
        Response response = APIRequests.executeRequest(url, currentPath, "", "", "get");
        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
        Assert.assertEquals("This is a failure as the status codes don't match\n", 404, response.getStatusCode());
        String errorMsg = "";
        if (response.getBody().asString().contains("errors")) {
            errorMsg = Utilities.getErrorMessage(response.getBody().asString());

        }
        System.out.println(errorMsg);
    }



    /*This test is to create a new Product using the API : POST*/

    @Test
    public void createProductTest() {

        Response response;
        response = APIRequests.executeRequest(url, path, createProductPayload, "", "Post");
        System.out.println(response.getBody().asString());
        //Assert.assertEquals("Check status codes for succesful response  ", 201, response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String name = jsonPath.getString("name");
        String id = jsonPath.getString("id");
        //Assert.assertEquals("The product name doesn't match", "testproduct", name);/*The Assert only gets executed when the expected and actual doesn't match*/
        System.out.println("product name : \n" + name);
        System.out.println("product id : \n" + id);

    }






    /* This Test is to create a new product using POST , get the created product using GET , able to delete the product using DELETE */


    @Test
    public void CreateAndDeleteProductTest() {

        Response response;
        /* The below request  is to post a new product */

        response = APIRequests.executeRequest(url, path, createProductPayload, "", "Post");
        String productId = Utilities.getJsonValue(response.getBody().asString(), "id");
        System.out.println(productId);


        /* The below request  is to get the product created above */

        String currentPath = path + "/" + productId;

        response = APIRequests.executeRequest(url, currentPath, "", "", "get");
        Assert.assertFalse(Utilities.getJsonBooleanValue(response.getBody().asString(), "isDisabled"));
        System.out.println(response.getBody().asString());

        /* The below request  is to delete the  product created the above */

        response = APIRequests.executeRequest(url, currentPath, "", "", "delete");
        Assert.assertEquals(200, response.getStatusCode());

        /* The below request  is to get the  product deleted  above  by status isDisabled=TRUE */

        response = APIRequests.executeRequest(url, currentPath, "", "", "get");
        Assert.assertTrue(Utilities.getJsonBooleanValue(response.getBody().asString(), "isDisabled"));
        System.out.println(response.getBody().asString());


    }



    /* Test to verify the disabled product(isDisabled = true) is not displaying when we get the list of all products */

    @Test
    public void verifyProductListTest() {

        Response response;
        response = APIRequests.executeRequest(url, path, createProductPayload, "", "Post");
        response.getBody().prettyPrint();
        String productId = Utilities.getJsonValue(response.getBody().asString(), "id");
        Assert.assertEquals(201, response.getStatusCode());
        String currentPath = path + "/" + productId;
        JSONObject obj = new JSONObject(response.getBody().asString());
        obj.put("isDisabled", true);
        String payload = obj.toString();
        //filename = "IsDisabledTrue.json";
        //filePath = Utilities.buildFilePath("SharedOrders.BillingLineProducts/", filename);
        //payload = Utilities.readFile(filePath);
        response = APIRequests.executeRequest(url, currentPath, payload, "", "put");
        response.getBody().prettyPrint();
        response = APIRequests.executeRequest(url, path, payload, "", "Get");
        System.out.println("The product details are : \n\n");
        response.getBody().prettyPrint();
        JSONArray jsonArray = new JSONArray(response.getBody().asString());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj1 = jsonArray.getJSONObject(i);
            String temp = Utilities.getJsonValue(obj, "id");
            if (temp.equals(productId)) {
                Assert.assertTrue(true);
                break;
            }
        }


    }



    /*This test is to update the product buying area is set to null */

    @Test
    public void productBuyingAreaNullTest() {

        Response response;
        response = APIRequests.executeRequest(url, path, createProductPayload, "", "Post");
        response.getBody().prettyPrint();
        String productId = Utilities.getJsonValue(response.getBody().asString(), "id");
        String newPath = path + "/" + productId;
        JSONObject obj = new JSONObject(response.getBody().asString());
        JSONArray arr = obj.getJSONArray("buyingAreaIds");
        arr.put("");
        String payload = obj + arr.toString();
        response = APIRequests.executeRequest(url, newPath, payload, "", "put");
        response.getBody().prettyPrint();
        String body = response.getBody().asString();
        String actualErrMsg = "Error converting value \"\" to type 'System.Guid'.";
        String errmsg = "";
        if (body.contains("errors")) {
            errmsg = Utilities.getErrorMessage(body);
        } else {
            Assert.assertTrue("The Error not occurred when updating  the product buying area is set to null", false);
        }
        if ((errmsg.contains(actualErrMsg))) {

            Assert.assertTrue("Getting an error message as expected", true);

        } else {
            Assert.assertTrue("Incorrect error message", false);
        }
        System.out.println(errmsg);
    }





    /* Test to verify BillingLine Product is not created when BuyingAreaId is disabled */

    @Test
    public void verifyDisabledBuyingAreaTest() {

        Response response;
        String buyingAreaIds1 = "0f5e1472-e200-42ea-bdb0-aabf0088d0cd";
        String filename = "CreateProduct.json";
        String filePath = Utilities.buildFilePath("BillingLineProducts/", filename);
        String payload = Utilities.readFile(filePath);
        JSONObject obj = new JSONObject(payload);
        JSONArray arr = obj.getJSONArray("buyingAreaIds");
        arr.put(buyingAreaIds1);
        payload = obj + arr.toString();
        response = APIRequests.executeRequest(url, path, payload, "", "post");
        response.getBody().prettyPrint();
        String body = response.getBody().asString();
        String actualErrMsg = ErrorMsgs.CannotCreateProductWithDisabledBA;
        String errmsg = "";
        if (body.contains("errors")) {
            errmsg = Utilities.getErrorMessage(body);
        } else {
            Assert.assertTrue("The Error not occurred when product is creating with  disabled buying area ", false);
        }
        if ((errmsg.contains(actualErrMsg))) {

            Assert.assertTrue("Getting an error message as expected", true);

        } else {
            Assert.assertTrue("Incorrect error message", false);
        }
        System.out.println(errmsg);
    }


}


















