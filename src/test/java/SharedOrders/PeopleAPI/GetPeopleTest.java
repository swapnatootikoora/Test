package SharedOrders.PeopleAPI;

import apirequests.APIRequests;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.EnvDetails;
import utilities.Utilities;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

public class GetPeopleTest {
    private  static String url="";
    private final static String path = "/people/api/People?Capability=Owner";
    private final static  String url2 ="/people/api/People";
    private static String fileSep = System.getProperty("file.separator");
    public static EnvDetails envDetails=new EnvDetails();


    @BeforeClass
    public static void getDataLoad() {
        String env = Utilities.envName();
        envDetails.envDetails(env);
        System.out.println(env);

        url = envDetails.url;
        System.out.println(url);
    }
    /*This returns all the owners*/
    @Test
    public void retrieveAllOwnersTest() {

        /*Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+path)
                .then().extract().response();*/
        Response response = APIRequests.executeRequest(url, path, "", "", "get");
        System.out.println("response body: \n\n");
         response.getBody().prettyPrint();
        System.out.println("status code : " + response.getStatusCode());
        System.out.println("content type:" + response.getContentType());


    }

    /*This test prints the first name and last name of the first owner in the list */
    @Test
    public void getFirstOwnerTest() {
       /* Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+path)
                .then().extract().response();*/
        Response response = APIRequests.executeRequest(url, path, "", "", "get");
        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);

        JSONArray jsonArray = new JSONArray(responseBody);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String firstName = jsonObject.getString("firstName");
        System.out.println(">>>>>> firstName : " + firstName);
        String lastName = jsonObject.getString("lastName");
        System.out.println(">>>>>> lastName : " + lastName);
       /* Assert.assertEquals("This is  failure , expetced and actual lastname din't match ", "Young", lastName);*/


    }

    /* This test returns an owner by owner id */
    @Test
    public void getOwnerByOwnerIdTest() {
        String  ownerID ="/fb8a501a-1fd9-4b1a-897d-aa9500a6c839";
        String currentPath=url2+ownerID;
        /*Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+url2+ownerID)
                .then().extract().response();*/
        Response response = APIRequests.executeRequest(url, currentPath, "", "", "get");
        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);

    }


    /*To test when the owner id doesn't exists, error 404 is returned back*/
    @Test
    public void invalidOwnerIdTest(){
        String invalidownerID ="/678op8a0-0664-407b-rt8d-bb985009g94a9";
        String currentPath=url2+invalidownerID;
        /*Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+url2+invalidownerID)
                .then().extract().response();*/

        Response response = APIRequests.executeRequest(url, currentPath, "", "", "get");

        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
        Assert.assertEquals("This is a failure as the status codes don't match\n",404,HttpStatus.SC_NOT_FOUND);
        System.out.println("The http status code is \n " +response.getStatusCode());
    }


    /*To test the error message when then the url is invalid : Expected status code is 400*/
    @Test
    public void invalidEndpointTest(){

        String path1 ="/";
        Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+path1)
                .then().extract().response();

        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
        System.out.println(" Response Status \n\n" +response.statusCode());
        Assert.assertEquals("Error message....for failure",404,response.getStatusCode());

    }

}






