package RBE;

import JsonClass.RBE;
import apirequests.APIRequests;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.EnvDetails;
import utilities.Utilities;

import java.util.Arrays;

public class RBETest {


    public static String url = "";
    private final static String path = "api/RBE/api/Rules";
    private final static String authentication = "";
    public static EnvDetails envDetails1 = new EnvDetails();

    /* setting up the env... details*/
    @BeforeClass
    public static void loadTestData() {
        String env = Utilities.envName();
        envDetails1.envDetails(env);
        System.out.println("RBE tests starting........environment:  " +env);

        url=envDetails1.RBEUrl;
        System.out.println(url);

    }

    /*String RBETestUrl="https://gssoniqavaildev.global.com:961/IntBuild/api/RBE/api/Rules";

  /*To test the error message when unauthorized : Expected status code is 401*/
    @Test
    public void unAuthorizationTest() {

        RBE rbe = new RBE();
        rbe.setGroupName(Arrays.asList(new String[]{"RBE1"}));
        rbe.setShowInputs(1);
        rbe.setAgency("C1290849");
        rbe.setClient("C234456");
        rbe.setLocality("Local");

        String payload = Utilities.createJsonObject(rbe);
        System.out.println(">>>>>>>\n\n\n" + payload);
        Response response = APIRequests.executeRequest(url, path, payload, "", "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("Error:Unauthorized  ", 401,response.getStatusCode());

        //Assert.assertTrue("Test failed as the response doesn't contain errors.", response.getBody().asString().contains("Error"));

        System.out.println(">>>>> " + response.getStatusCode());
        String actualErrMsg = "Error: Unauthorized";
        String errmsg = "";
        if (response.equals(actualErrMsg)) {

            Assert.assertTrue("Getting an error message as expected", true);
        }

        System.out.println(response.getBody().asString());

    }

    /*This test is to return rules using params Local  using the API : POST*/
    @Test
    public void createRBERulesLocalTest() {

        RBE rbe = new RBE();
        rbe.setGroupName(Arrays.asList(new String[]{"RBE1"}));
        rbe.setShowInputs(1);
        rbe.setAgency("C1290849");
        rbe.setClient("C234456");
        rbe.setLocality("Local");

        String payload = Utilities.createJsonObject(rbe);
        System.out.println(">>>>>>>\n\n\n" + payload);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }
    /*This test is to return rules using params National  using the API : POST*/
    @Test
    public void createRBERulesNationalTest() {

        RBE rbe = new RBE();
        rbe.setGroupName(Arrays.asList(new String[]{"RBE1"}));
        rbe.setShowInputs(1);
        rbe.setAgency("C1290849");
        rbe.setClient("C234456");
        rbe.setLocality("National");

        String payload = Utilities.createJsonObject(rbe);
        System.out.println(">>>>>>>\n\n\n" + payload);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }
    /*This test is to return rules using params Local with Agency null  using the API : POST*/
    @Test
    public void createRBERulesAgencyNullTest() {

        RBE rbe = new RBE();
        rbe.setGroupName(Arrays.asList(new String[]{"RBE1"}));
        rbe.setShowInputs(1);
        rbe.setAgency(null);
        rbe.setClient("C234456");
        rbe.setLocality("Local");

        String payload = Utilities.createJsonObject(rbe);
        System.out.println(">>>>>>>\n\n\n" + payload);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }


    /*This test is to return rules using params Local with Client null  using the API : POST*/
    @Test
    public void createRBERulesClientNullTest() {

        RBE rbe = new RBE();
        rbe.setGroupName(Arrays.asList(new String[]{"RBE1"}));
        rbe.setShowInputs(1);
        rbe.setAgency("C1290849");
        rbe.setClient(null);
        rbe.setLocality("Local");

        String payload = Utilities.createJsonObject(rbe);
        System.out.println(">>>>>>>\n\n\n" + payload);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }
    /*This test is to return rules using params Local with Client and Agency null  using the API : POST*/
    @Test
    public void createRBERulesAgencyAndClientNullTest() {

        RBE rbe = new RBE();
        rbe.setGroupName(Arrays.asList(new String[]{"RBE1"}));
        rbe.setShowInputs(1);
        rbe.setAgency(null);
        rbe.setClient(null);
        rbe.setLocality("Local");

        String payload = Utilities.createJsonObject(rbe);
        System.out.println(">>>>>>>\n\n\n" + payload);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }
    /*This test is to return rules using params National with  Agency null  using the API : POST*/
    @Test
    public void createRBERulesNationalAgencyNullTest() {

        RBE rbe = new RBE();
        rbe.setGroupName(Arrays.asList(new String[]{"RBE1"}));
        rbe.setShowInputs(1);
        rbe.setAgency(null);
        rbe.setClient("C234456");
        rbe.setLocality("National");

        String payload = Utilities.createJsonObject(rbe);
        System.out.println(">>>>>>>\n\n\n" + payload);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }



    /*This test is to return rules using params National with  Client null  using the API : POST*/
    @Test
    public void createRBERulesNationalClientNullTest() {

        RBE rbe = new RBE();
        rbe.setGroupName(Arrays.asList(new String[]{"RBE1"}));
        rbe.setShowInputs(1);
        rbe.setAgency("C1290849");
        rbe.setClient(null);
        rbe.setLocality("National");

        String payload = Utilities.createJsonObject(rbe);
        System.out.println(">>>>>>>\n\n\n" + payload);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }


    /*This test is to return rules using params Local with Client and Agency null  using the API : POST*/
    @Test
    public void createRBERulesNationalAgencyAndClientNullTest() {

        RBE rbe = new RBE();
        rbe.setGroupName(Arrays.asList(new String[]{"RBE1"}));
        rbe.setShowInputs(1);
        rbe.setAgency(null);
        rbe.setClient(null);
        rbe.setLocality("National");

        String payload = Utilities.createJsonObject(rbe);
        System.out.println(">>>>>>>\n\n\n" + payload);
        Response response = APIRequests.executeRequest(url, path, payload, authentication, "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }


}
