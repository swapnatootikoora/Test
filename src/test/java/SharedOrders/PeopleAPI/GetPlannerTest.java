package SharedOrders.PeopleAPI;

import SharedOrders.PeopleAPI.Person;
import apirequests.APIRequests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import org.slf4j.LoggerFactory;
import utilities.EnvDetails;
import utilities.Utilities;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GetPlannerTest {
    public  static String url = "";
    private final static String plannerPath = "/people/api/People?Capability=Planner";
    private final static String peoplePath = "/people/api/People";
    private final static String ownerPlannerPath = "/people/api/People?Capability=Owner&Capability=Planner";
    private final static String capabilityNullPath = "/people/api/People?Capability=";
    //private static String fileSep = System.getProperty("file.separator");
    public static EnvDetails envDetails=new EnvDetails();

    /* setting up the env... details*/
    @BeforeClass
    public static void setUp() {
        String env= Utilities.envName();
        envDetails.envDetails(env);
        System.out.println(env);
        url=envDetails.url;
        System.out.println(url);
    }

    /*This returns all the Planners*/
    @Test
    public void retrieveAllPlannerTest() {
        Response response = APIRequests.executeRequest(url, plannerPath, "", "", "get");
       /* Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+peoplePath)
                .then().extract().response();*/
        System.out.println("response body: \n\n" );
        response.getBody().prettyPrint();
        System.out.println("status code : " + response.getStatusCode());
        System.out.println("content type:" + response.getContentType());
        Assert.assertNotNull(response);
        Assert.assertEquals("This is a failure as the statuscode don't match\n", response.getStatusCode(), 200);
    }

    /*This test prints the first name and last name of the first planner in the list */
    @Test
    public void getFirstPlannerTest() {
        /*Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+plannerPath)
                .then().extract().response();*/

        Response response = APIRequests.executeRequest(url, plannerPath, "", "", "get");
        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
        JSONArray jsonArray = new JSONArray(responseBody);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String firstName = jsonObject.getString("firstName");
        System.out.println(">>>>>> firstName : " + firstName);
        String lastName = jsonObject.getString("lastName");
        System.out.println(">>>>>> lastName : " + lastName);
        Assert.assertTrue(responseBody.contains("firstName"));
        Assert.assertTrue(responseBody.contains("lastName"));
    }

    /* This test returns an Planner by Planner id */
   @Test
    public void getPlannerByPlannerIdTest() {
        // String  plannerID ="/f80fb11f-b1af-433d-ba8b-aa9500a6c839";
       Response response = APIRequests.executeRequest(url, plannerPath, "", "", "get");
        /*Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+plannerPath)
                .then().extract().response();*/
        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
        JSONArray jsonArray = new JSONArray(responseBody);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String id = jsonObject.getString("id");
        System.out.println(">>>>>> id : " + id);
        String currentPath = peoplePath + "/" + id;
        Response response1 = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url + currentPath)
                .then().extract().response();
        String responseBody1 = response1.asString();
        Person people = response1.as(Person.class);
        Assert.assertNotNull(people.id);
        Assert.assertNotNull(people.firstName);
        Assert.assertNotNull(people.lastName);
        Assert.assertNotNull(people.capabilities);
        Assert.assertNotNull(people.isDisabled);


        // Assert.assertTrue(Arrays.stream(people).anyMatch(person -> Arrays.stream(person.capabilities).anyMatch(i -> i == 1)));
        //Assert.assertTrue(Arrays.stream(people).anyMatch(person -> Arrays.stream(person.capabilities).anyMatch(i -> i == 2)));
// Assert.assertTrue(Arrays.stream(people).anyMatch(person -> Arrays.stream(person.capabilities).anyMatch(i -> i == 1) && Arrays.stream(person.capabilities).anyMatch(i -> i == 2)));

    }

    /*To test when the Planner id doesn't exists, error 404 is returned back*/

    @Test
    public void invalidPlannerIdTest() {
        String invalidPlannerID = "/2fb5c200-818e-4033-b637-aa9500a6c832";
        String currentPath=peoplePath+invalidPlannerID;
        Response response = APIRequests.executeRequest(url, currentPath, "", "", "get");
        /*Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url + invalidPlannerID)
                .then().extract().response();*/
        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
        Assert.assertEquals("This is a failure as the status codes don't match\n", 404, HttpStatus.SC_NOT_FOUND);
        System.out.println("The http status code is \n " + response.getStatusCode());
    }

    /*This returns all the planner and owners*/
    @Test

    public void retrieveAllPlannerOwnerTest() {

       /* Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+ownerPlannerPath)
                .then().extract().response();*/


        Response response = APIRequests.executeRequest(url, ownerPlannerPath, "", "", "get");
        System.out.println("response body: \n\n");
        response.getBody().prettyPrint();
        System.out.println("status code : " + response.getStatusCode());
        System.out.println("content type:" + response.getContentType());
        Person[] people = response.as(Person[].class);
    }


    /*To test the error message when capability status is null : Expected status code is 400*/
    @Test
    public void nullCapabilityTest() {
        //Response response = APIRequests.executeRequest(url, capabilityNullPath, "", "", "get");
        Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+capabilityNullPath)
                .then().extract().response();
        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
        System.out.println(" Response Status \n\n" + response.statusCode());
        Assert.assertEquals("Error message....for failure", 400,response.getStatusCode());

    }
/* To test  all the Active Planners,each Planner containing First Name and Last Name,order by First Name,Last Name*/
    @Test
    public void capabilityPlannerTest() {
         List<Integer> cap = new ArrayList<Integer>();
        List<String> names = new ArrayList<String>();
        List<String> sortedNames = new ArrayList<String>();

        Response response = given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url+plannerPath)
                .then().extract().response();

        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
        System.out.println(" Response Status \n\n" + response.statusCode());

        JSONArray jsonArray = new JSONArray(responseBody);
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            boolean flag = obj.getBoolean("isDisabled");
            Assert.assertFalse("The value of is isDisabled field is " + flag, flag);
            System.out.println(obj.getBoolean("isDisabled"));
           JSONArray arr = obj.getJSONArray("capabilities");
            for (int j = 0; j < arr.length(); j++){
                cap.add(arr.getInt(j));

            }
            if(cap.contains(2))
            {
               Assert.assertTrue(true);
            }
            else
            {
                Assert.assertTrue(false);
            }
            String fName = obj.getString("firstName");
            String lName = obj.getString("lastName");
            if(fName == null || fName.isEmpty() || lName == null || lName.isEmpty()){

                Assert.assertTrue(false);
            }
            names.add(fName+", " +lName);
        }
       sortedNames = names;
        //sortedNames.sort(Comparator.comparing( String::toString ));
        if (names.equals(sortedNames)){
            Assert.assertTrue(true);

        }
        else
        {
            Assert.assertTrue(false);
        }
        System.out.println(sortedNames);
        System.out.println(names);

    }

}











