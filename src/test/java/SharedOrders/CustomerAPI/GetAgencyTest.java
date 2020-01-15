package SharedOrders.CustomerAPI;
import  io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import javafx.beans.binding.BooleanExpression;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class GetAgencyTest {

    private final static String url = "https://gcotest.global.com/customers/api/Agencies";
    private static String fileSep = System.getProperty("file.separator");

    /*This returns all the Agencies and then returns an agency by id*/
    @Test
    public void getAllAgencies() {


        Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url)
                .then().extract().response();
        System.out.println("response body: \n\n" + response.getBody().prettyPrint());
        System.out.println("status code : " + response.getStatusCode());
        System.out.println("content type:" + response.getContentType());


        /* The test steps below are to get an agency using its id*/
        JSONArray jsonResponse = new JSONArray(response.asString());
        String id = jsonResponse.getJSONObject(0).getString("id");

                    Response response1 = given()
                    .contentType("application/json").accept(ContentType.JSON)
                    .when()
                    .get(url + fileSep + id)
                    .then().extract().response();

            JsonPath jsonPath = new JsonPath(response1.getBody().asString());
            System.out.println("The agency details are : \n" + response1.getBody().prettyPrint());



        /* Test to get all active contacts for an agency */
        Response response4 = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url + fileSep + id + fileSep + "contacts")
                .then().extract().response();
        jsonPath = new JsonPath(response4.getBody().asString());
        System.out.println("The agency contact details are : \n" + response4.getBody().prettyPrint());




        /*Test to get a single contact for an agency*/
        JSONArray jsonResponse5 = new JSONArray(response4.asString());
        String id2 = jsonResponse5.getJSONObject(0).getString("id");
        Response response5 = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url + fileSep + id + fileSep + "contacts" + fileSep + id2)
                .then().extract().response();

        jsonPath = new JsonPath(response5.getBody().asString());
        System.out.println("The client contact details for this agency are : \n" + response5.getBody().prettyPrint());



        /* Test to get all clients for a specific agency*/
        Response response2 = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url + fileSep + id + fileSep + "clients")
                .then().extract().response();
        jsonPath = new JsonPath(response2.getBody().asString());
        System.out.println("The agency clients details are : \n" + response2.getBody().prettyPrint());



        /* To get a single client for a specific agency*/
        JSONArray jsonResponse2 = new JSONArray(response2.asString());
        String id1 = jsonResponse2.getJSONObject(2).getString("id");
        Response response3 = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url + fileSep + id + fileSep + "clients" + fileSep + id1)
                .then().extract().response();

        jsonPath = new JsonPath(response3.getBody().asString());
        System.out.println("The client details for this agency are : \n" + response3.getBody().prettyPrint());





    }



}



