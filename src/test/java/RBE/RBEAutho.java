package RBE;


import apirequests.APIRequests;
import gherkin.deps.com.google.gson.JsonObject;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.EnvDetails;
import utilities.Utilities;

import java.io.IOException;

public class RBEAutho {

    /* This test for Implement API Security, has been implemented to expect a valid Jwt current authentication token.
    And prints the GroupName*/
    public static String url = "";
   private final static String path = "api/RBE/api/Rules";
    private final static String authentication = "";
    public static EnvDetails envDetails1 = new EnvDetails();

    /* setting up the env... details*/
    @BeforeClass
    public static void loadTestData() {
        String env = Utilities.envName();
        envDetails1.envDetails(env);
        System.out.println(env);

        url=envDetails1.RBEUrl;
        System.out.println(url);

    }


/* This test is to get the Rules using valid params for Locality is Local*/

    @Test
    public void getRulesWithLocal() throws IOException {

        String filename = "ValidGroupName.json";
        String filePath = Utilities.buildFilePath("RBE/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        response.getBody().prettyPrint();
        System.out.println(">>>>> " + response.getStatusCode());
        //String s = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("GroupName : \n" + jsonPath.getString("groupName"));
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        System.out.println("The http status code is \n " + response.getStatusCode());

    }

    /* This test is to get the Rules using valid params for Locality is National*/

    @Test
    public void getRulesWithNational() throws IOException {
        String filename = "ValidGroupName.json";
        String filePath = Utilities.buildFilePath("RBE/", filename);
        String payload = Utilities.readFile(filePath);
       JSONObject obj=new JSONObject(payload);
        obj.put("Locality","National");
        payload = obj.toString();
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        response.getBody().prettyPrint();
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("GroupName : \n" + jsonPath.getString("groupName"));
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        System.out.println("The http status code is \n " + response.getStatusCode());

    }


    @Test

/* This test for Implement API Security, has been implemented to expect a valid Jwt current authentication token.
And if a Group Name is NOT passed in, then the default values are used from the config*/

    public void invalidGroupNameTest() throws IOException {


        String filename = "GroupNull.json";
        String filePath = Utilities.buildFilePath("RBE/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("GroupName : \n" + jsonPath.getString("groupName"));
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
    }


    @Test

    /*To test the error message when pass in Client but not Agency, error 500 : Internal Server Error*/

    public void nullAgencyTest() throws IOException {

        String filename = "AgencyNull.json";
        String filePath = Utilities.buildFilePath("RBE/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        System.out.println(">>>>> " + response.getStatusCode());
        //String s = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        //System.out.println("GroupName : \n"+jsonPath.getString("groupName"));
        Assert.assertEquals("This is a failure as the status codes don't match\n", 500, response.getStatusCode());
        System.out.println("The http status code is \n " + response.getStatusCode());


    }

    @Test

    /*To test the error message when Locality is null, error 500 : Internal Server Error*/

    public void nullLocalityTest() throws IOException {

        String filename = "LocalityNull.json";
        String filePath = Utilities.buildFilePath("RBE/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        System.out.println(">>>>> " + response.getStatusCode());
        //String s = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        //System.out.println("GroupName : \n"+jsonPath.getString("groupName"));
        Assert.assertEquals("This is a failure as the status codes don't match\n", 500, response.getStatusCode());
        System.out.println("The http status code is \n " + response.getStatusCode());


    }

    @Test

    /*To test the error message when Client is null, error 500 : Internal Server Error*/

    public void nullClientTest() throws IOException {

        String filename = "NullClient.json";
        String filePath = Utilities.buildFilePath("RBE/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        System.out.println(">>>>> " + response.getStatusCode());
        Assert.assertEquals("This is a failure as the status codes don't match\n", 500, response.getStatusCode());
    }

    @Test

    /*To test the error message when ShowInputs as entered string instead of numerical value*/

    public void BadRequest() throws IOException {

        String filename = "BadRequest.json";
        String filePath = Utilities.buildFilePath("RBE/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("Given Show Inputs as text instead of numericals \n", 400, response.getStatusCode());

    }



    @Test
    /*To test the error message when unauthorized : Expected status code is 401*/

    public void unAuthoTest() throws IOException {

        String filename = "ValidGroupName.json";
        String filePath = Utilities.buildFilePath("RBE/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, "", "Post");
        System.out.println(">>>>> " + response.getStatusCode());
        //String s = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        //System.out.println("GroupName : \n"+jsonPath.getString("groupName"));
        Assert.assertEquals("This is a failure as the status codes don't match\n", 401, response.getStatusCode());
        System.out.println("The http status code is \n " + response.getStatusCode());


    }
    /* Verify all the tests at one go */
/*@Test
    public void RBEFramework(){

    String filename = "Testcase1.json";
    String filePath = Utilities.buildFilePath("RBE/",filename);
    String payload= Utilities.readFile(filePath);
    //String filePath="///Users/ashwini.brahmavar/gco-Testing/testdata/Testcase1.json";
    //String data = Utilities.readFile(filePath);
    JSONObject obj = new JSONObject(payload);
    JSONArray arr = obj.getJSONArray("files");
    for(int i=0;i<arr.length();i++)
    {
        JSONObject obj1 = arr.getJSONObject(i);
        String fileName = obj1.getString("filePath");
        String body = Utilities.readFile(fileName);
        RequestBuilder.buildRequest(obj.getString("url"),obj.getString("path"),body,obj.getString("auth"),obj.getString("method"));
    }
    }*/

}



