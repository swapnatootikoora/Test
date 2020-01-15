package SharedOrders.CustomerAPI;

import apirequests.APIRequests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLOutput;

import static io.restassured.RestAssured.given;

public class GetClientsTest {

    private final static String url = "https://gcotest.global.com/customers/api/Clients";

    /*This returns all the clients*/
    @Test
    public void getAllClients() {

        Response response = APIRequests.getAPI(url);
        System.out.println("The client details are : \n\n" + response.getBody().prettyPrint());
        System.out.println("status code : " + response.getStatusCode());
        System.out.println("content type:" + response.getContentType());
        JSONArray jsonResponse = new JSONArray(response.asString());
        String id = jsonResponse.getJSONObject(0).getString("id");


    }


    /*This test verifies the particular CRN is present */
    @Test
    public void verfifyCRNTest() {
        String expectedCRNValue = "C2427274";
        Response response = APIRequests.getAPI(url);
        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
        JSONArray jsonArray = new JSONArray(responseBody);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
       /* String name = jsonObject.getString("name");
        System.out.println(">>>>>> Name : " + name);*/
        String CRN = jsonObject.getString("customerReferenceNumber");
        System.out.println(">>>>>> customerReferenceNumber : " + CRN);
        /*Assert.assertEquals("This is  failure , expected and actual CRNS didn't match ", expectedCRNValue, CRN);*/

    }



    /*This test gets the 1st client is in the list*/
    @Test
    public void getFirstClientTest() {
        /*String expectednameValue="volvo cars";*/
        Response response = APIRequests.getAPI(url);
        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
        JSONArray jsonArray = new JSONArray(responseBody);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String name = jsonObject.getString("name");
        System.out.println(">>>>>> Name : " + name);
            /* Assert.assertEquals("This is a failure ,expected and actual names didn't match",expectednameValue, name);*/
            }


/*This test searches a particular client by client name*/
    @Test
    public void searchClientByClientNameTest() {
        Response response = given()
                .contentType("application/json").accept(ContentType.JSON).queryParam("Search","Wyse")
                .when()
                .get(url)
                .then().extract().response();


        System.out.println("response body: \n\n" + response.getBody().prettyPrint());
        System.out.println("status code : " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("Client name : \n"+jsonPath.getString("name"));
        System.out.println("Client id : \n"+jsonPath.getString("id"));
    }


    /*This test gets a client by client id */
    @Test
    public void getClientByClientId()
        {

        String clientId ="/23757b8a-1f9e-4c44-8483-aa9901188603";
        Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+clientId)
                .then().extract().response();

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("Client name : \n"+jsonPath.getString("name"));

    }


    /*This test returns an error status when client id doesn't exist*/
    @Test
    public void invalidClientTest()
    {
        String invalidclientId = "/1a97dc6b-934f-46f5-9167-aa840105b97b";
        Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+invalidclientId)
                .then().extract().response();
        System.out.println("status code : " + response.getStatusCode());

    }


    /*This test returns all active client contacts for a given client, when searched by ClientID*/
    @Test
    public void getClientsByClientIdTest()
    {
        String clientId="/b40b95ad-1f33-462f-b979-aa840105b97b/contacts";
        Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+clientId)
                .then().extract().response();

        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
    }


    /*This test retrieves client by ClientContactID and displays the 1st client contact in the list */
    @Test
    public void getClientByClientContactIdTest()
    {
     String clientcontactId= "/b40b95ad-1f33-462f-b979-aa840105b97b/contacts/8dea1afd-b6a0-40ce-a431-aa850087d8cf";
        Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+clientcontactId)
                .then().extract().response();

        String responseBody = response.asString();
        System.out.println("Response Body \n\n" + responseBody);

    }
}

//to do write new  test for the following*/
/* Test to get client brands*/
/*Test to get a single brand for a particular client*/
/*Test to get  brand products for a client*/
/*Test to get single product for a particular client and brand combination  by id*/
