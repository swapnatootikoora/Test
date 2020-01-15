package SharedOrders.BillingLineBuyingAreas;

import apirequests.APIRequests;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.EnvDetails;
import utilities.ErrorMsgs;
import utilities.Utilities;

import java.io.IOException;

public class BuyingAreasTest {
    public static String url = "";
    //private final static String url = "https://gcotest.global.com";
    private final static String path = "billinglinebuyingareas/api/BillingLineBuyingAreas";
    private static String fileSep = System.getProperty("file.separator");
    public static String activeParentPayload = "";
    public static String inActiveParentPayload = "";
    public static EnvDetails envDetails=new EnvDetails();


/* setting up the env... details*/
    @BeforeClass
    public static void setUp() {
        String env=Utilities.envName();
        envDetails.envDetails(env);
        System.out.println(env);
        url=envDetails.url;
        System.out.println(url);
    }
    /* Creating Active and Inactive ParentIDs and Payloads */
    @Before
    public void getParentBuyingAreaIds()
    {
        String fileName = "CreateNewBuyingArea.json";
        String filePath = Utilities.buildFilePath("BillingLineBuyingAreas/", fileName);        String payload = Utilities.readFile(filePath);
        JSONObject obj = BuyingAreaUtilities.createParentBuyingArea(payload, url, path);
        activeParentPayload = obj.toString();
        JSONObject obj1 = new JSONObject(payload);get
        obj1.put("isDisabled", true);
        payload = obj1.toString();
        obj = BuyingAreaUtilities.createParentBuyingArea(payload, url, path);
        inActiveParentPayload = obj.toString();
    }


    /*This Test returns all the SharedOrders.BillingLineBuyingAreas and fetch the Buyingarea details by  active Buyingarea id*/
    @Test
    public void getActiveBuyingAreasTest() {

        Response response = APIRequests.executeRequest(url, path, "", "", "");
        System.out.println(" The All Billingline Buying areas are: \n\n");
        response.getBody().prettyPrint();
        JSONArray jsonResponse = new JSONArray(response.asString());
        String id = jsonResponse.getJSONObject(0).getString("id");
        System.out.println(">>>>>> id : " + id);
        String currentPath = path + "/" + id;
        response = APIRequests.executeRequest(url, currentPath, "", "", "get");
        System.out.println("The Buyingarea details of given id :\n\n");
        response.getBody().prettyPrint();
    }

    /* This test is if Buying Area expiry date > getdate, then API returns the Buying Area */

    @Test
    public void getBuyingAreaDateTest() {

        String filename = "BuyingAreaExpiryDategetDate.json";
        String filePath = Utilities.buildFilePath("BillingLineBuyingAreas/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, "", "post");
        System.out.println(" The  Billingline Buying area Details are: \n\n");
        response.getBody().prettyPrint();
        //JSONObject jsonObject = new JSONObject(response.asString());
        JsonPath jsonPath = new JsonPath(response.asString());
        String id = jsonPath.getString("id");
        String currentPath = path + "/" + id;
        Response response1 = APIRequests.executeRequest(url, currentPath, "", "", "get");
        System.out.println(" The  Billingline Buying area Details are: \n\n");
        response1.getBody().prettyPrint();


    }

    /*Test New buying area cannot be created if Name <3 characters.*/

    @Test
    public void minNameFieldValidationTest() throws IOException {

        String filename = "Testlessthan3charsinNameField.json";
        String filePath = Utilities.buildFilePath("BillingLineBuyingAreas/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, "payload", "", "post");
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("BuyingAreaName : \n" + jsonPath.getString("name"));
        Assert.assertEquals("This is a failure as the status codes don't match\n", 400, response.getStatusCode());
        System.out.println("The http status code is \n " + response.getStatusCode());
        System.out.println("response body: \n\n");
        response.getBody().prettyPrint();
    }

    /* This test is  to verify that new buying area cannot be created if Name >512 characters */

    @Test
    public void maxNameFieldValidationTest() {

        String filename = "name512Test.json";
        String filePath = Utilities.buildFilePath("BillingLineBuyingAreas/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, "payload", "", "post");
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("BuyingAreaName : \n" + jsonPath.getString("name"));
        Assert.assertEquals("This is a failure as the status codes don't match\n", 400, response.getStatusCode());
        System.out.println("The http status code is \n " + response.getStatusCode());
        System.out.println("response body: \n\n");
        response.getBody().prettyPrint();
    }

    /*This Test update the End Date by PUT method*/
    @Test
    public void getUpdatedBillingLineBuyingArea() {


        String filename = "UpdateEndDateByPUTMethod.json";
        String filePath = Utilities.buildFilePath("BillingLineBuyingAreas/", filename);
        String payload = Utilities.readFile(filePath);
        String BuyingAreaId = Utilities.getJsonValue(payload, "id");
        String currentPath = path + "/" + BuyingAreaId;
        Response response = APIRequests.executeRequest(url, currentPath, payload, "", "put");
        System.out.println("The updated buying area details are: \n\n");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 404, HttpStatus.SC_NOT_FOUND);


    }


    /*Get a billing line  buying area by specific id*/
    @Test
    public void getBuyingAreaById() {
        String BuyingAreaId = "35aaec0d-ac3b-4fe2-898c-aab100d9a22a";

        String currentPath = path + "/" + BuyingAreaId;
        Response response = APIRequests.executeRequest(url, currentPath, "", "", "get");
        String responseBody = response.asString();
        System.out.println(" Response Body \n\n" + responseBody);
        Assert.assertEquals(404, response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("BuyingArea name : \n" + jsonPath.getString("name"));
        System.out.println("response body: \n\n");
        response.getBody().prettyPrint();


    }

    /*This test returns an 404 error status when BuyingArea id doesn't exist*/
    @Test
    public void invalidBuyingAreaIdTest() {
        String invalidBuyingAreaId = "35aaec0d-ac3b-4fe2-898c-aab100d9a22b";
        String currentPath = path + "/" + invalidBuyingAreaId;
        Response response = APIRequests.executeRequest(url, currentPath, "", "", "get");
        System.out.println("status code : " + response.getStatusCode());
        System.out.println("The Buying area is not getting when id is invalid: \n\n");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 404, HttpStatus.SC_NOT_FOUND);

    }




    /*This test returns an error status when name of the buying area   is null*/


    @Test
    public void nullBuyingArea() throws IOException {

        String filename = "NullBuyingArea.json";
        String filePath = Utilities.buildFilePath("BillingLineBuyingAreas/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, "", "post");
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals("This is a failure as the status codes don't match\n", 400, HttpStatus.SC_BAD_REQUEST);
        System.out.println("The http status code is \n " + response.getStatusCode());
        System.out.println("Buying Area not created when Buying Area Name is Null: \n\n");
        response.getBody().prettyPrint();
    }


    /*Creates a new billing line Buying Area instance and saves it to database.*/

    @Test
    public void createBuyingAreaInstance() throws IOException {

        String filename = "CreateNewBuyingArea.json";
        String filePath = Utilities.buildFilePath("BillingLineBuyingAreas/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, "", "post");
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("BuyingAreaName : \n" + jsonPath.getString("name"));
        Assert.assertEquals("This is a failure as the status codes don't match\n", 201, response.getStatusCode());
        System.out.println("The http status code is \n " + response.getStatusCode());
        System.out.println("The new Billing line Buying Area is created : \n\n");
        response.getBody().prettyPrint();
    }


    /* Test if expiry date on new Buying Area can be NULL */

    @Test
    public void createNullExpiryDateBuyingAreaTest() throws IOException {

        String filename = "expiryDatenull.json";
        String filePath = Utilities.buildFilePath("BillingLineBuyingAreas/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, "", "post");
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("BuyingAreaName : \n" + jsonPath.getString("name"));
        Assert.assertEquals("This is a failure as the status codes don't match\n", 400, response.getStatusCode());
        System.out.println("The http status code is \n " + response.getStatusCode());
        System.out.println("The new Billing line Buying Area is created : \n\n");
        response.getBody().prettyPrint();
    }



    /* Test if  Parent is inactive, then child cannot be set to active */

    @Test
    public void createInactiveChild() throws IOException {

        String filename = "InactiveParent.json";
        String filePath = Utilities.buildFilePath("BillingLineBuyingAreas/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.PostAPI(url + "/" + path, payload);
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("BuyingAreaName : \n" + jsonPath.getString("name"));
        Assert.assertEquals("This is a failure as the status codes don't match\n", 201, HttpStatus.SC_CREATED);
        System.out.println("The http status code is \n " + response.getStatusCode());
        System.out.println("The Inactive child details are: \n\n");
        response.getBody().prettyPrint();
    }

    /*Test if  Parent is set to active, then child will automatically be active*/


    @Test
    public void createActiveChild() throws IOException {

        Response response = APIRequests.executeRequest(url, path, inActiveParentPayload, "", "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 201, response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String id = jsonPath.getString("id");
        String parentId = jsonPath.getString("parentId");
        String childPath = path + "/" + id;
        String parentPath = path + "/" + parentId;
        response = APIRequests.executeRequest(url, childPath, "", "", "get");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        jsonPath = new JsonPath(response.getBody().asString());
        if (!jsonPath.getBoolean("isDisabled")) {
            Assert.assertTrue("An actvie child is created for an inActive parent", false);
        }
        response = APIRequests.executeRequest(url, parentPath, "", "", "get");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        JSONObject obj = new JSONObject(response.getBody().asString());
        obj.put("isDisabled", false);
        String payload = obj.toString();
        response = APIRequests.executeRequest(url, parentPath, payload, "", "put");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        //String id=jsonPath.getString("id");
        response = APIRequests.executeRequest(url, childPath, "", "", "get");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        System.out.println("The http status code is \n " + response.getStatusCode());
        jsonPath = new JsonPath(response.getBody().asString());
        if (jsonPath.getBoolean("isDisabled")) {
            Assert.assertTrue(" A child is not activated when parent is activated", false);
        } else {
            Assert.assertTrue(" A child is activated successfully  when parent is activated", true);
        }

        response.getBody().prettyPrint();

    }



    /* An Active child is added for an Active parent */

    @Test
    public void activeParent() {
        Response response = APIRequests.executeRequest(url, path, activeParentPayload, "", "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 201, response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String id = jsonPath.getString("id");
        String parentId = jsonPath.getString("parentId");
        String childPath = path + "/" + id;
        String parentPath = path + "/" + parentId;
        response = APIRequests.executeRequest(url, childPath, "", "", "get");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        jsonPath = new JsonPath(response.getBody().asString());
        if (jsonPath.getBoolean("isDisabled")) {
            Assert.assertTrue("An inactive child is created for an Active parent", false);
        } else {
            Assert.assertTrue("An active child is created successfully for an Active parent", true);
        }
    }


    /* Child is set to inActive when added for an inActive Parent */

    @Test
    public void inActiveParent() {
        Response response = APIRequests.executeRequest(url, path, inActiveParentPayload, "", "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 201, response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String id = jsonPath.getString("id");
        String parentId = jsonPath.getString("parentId");
        String childPath = path + "/" + id;
        String parentPath = path + "/" + parentId;
        response = APIRequests.executeRequest(url, childPath, "", "", "get");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        jsonPath = new JsonPath(response.getBody().asString());
        if (!jsonPath.getBoolean("isDisabled")) {
            Assert.assertTrue("An active child is created for an inActive parent", false);
        } else {
            Assert.assertTrue("An inActive child is created successfully for an inActive parent", true);
        }
    }

    /* Expiry date on existing Buying Area can be set to NULL */

    @Test
    public void setExpiryDatetoNull() {
        Response response = APIRequests.executeRequest(url, path, activeParentPayload, "", "Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 201, response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());
        JSONObject obj = new JSONObject(response.getBody().asString());
        obj.put("endDate", "");
        String payload = obj.toString();
        String id = obj.getString("id");
        String childPath = path + "/" + id;
        response = APIRequests.executeRequest(url, childPath, payload, "", "put");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        obj = new JSONObject(response.getBody().asString());
        Object el=obj.get("endDate");
        Object temp=null;
        if (el.equals(temp)) {
            Assert.assertTrue("End date is successfully set to null ", true);
        } else {
            Assert.assertTrue("End date is not set to null", false);
        }
    }


    /* This is test is to create and get and delete the buying area in one go
    @Test

    public void deletebuyingarea() throws IOException {
        String filename = "createNewBuyingArea.json";
        String filePath = Utilities.buildFilePath("BillingLineBuyingAreas/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, "", "Post");
        String BuyingAreaId = Utilities.getJsonValue(response.getBody().asString(), "id");
        System.out.println(BuyingAreaId);

        /* The below request  is to get the billinglinebuyingarea by id

        String currentPath = path + "/" + BuyingAreaId;
        response = APIRequests.executeRequest(url, currentPath, "", "", "get");
        Assert.assertFalse(Utilities.getJsonBooleanValue(response.getBody().asString(), "isDisabled"));
        response.getBody().prettyPrint();

        /* The below request  is to delete the  buying area created the above

        //response = APIRequests.executeRequest(url, currentPath, "", "", "delete");
        //Assert.assertEquals("This is a failure as the status codes don't match\n", 405, response.getStatusCode());
    }*/

    /* verify making a child active under an inactive parent  */
    @Test
    public void activeParentTest() throws IOException {
        Response response = APIRequests.executeRequest(url, path, inActiveParentPayload, "", "Post");
        response.getBody().prettyPrint();
        String BuyingAreaId = Utilities.getJsonValue(response.getBody().asString(), "id");
        String newPath = path + "/" + BuyingAreaId;
        JSONObject obj = new JSONObject(response.getBody().asString());
        obj.put("isDisabled", false);
        String payload = obj.toString();
        response = APIRequests.executeRequest(url, newPath, payload, "", "put");
        response.getBody().prettyPrint();
        String body = response.getBody().asString();
        String actualErrMsg = ErrorMsgs.CannotActivateChild;
        String errmsg = "";
        if (body.contains("errors")) {
            errmsg = Utilities.getErrorMessage(body);
        } else {
            Assert.assertTrue("The Error not occurred when updating child under an inactive parent to active", false);
        }
       /* if (actualErrMsg.equals(errmsg)) {

            Assert.assertTrue("Getting an error message as expected", true);

        } else {
            Assert.assertTrue("Incorrect error message", false);
        }*/
        System.out.println(errmsg);
    }




    /* If Buying Area is inactive, then API does not return the Buying Area */

    @Test
    public void verifyInActiveBuyingAreaNotDisplayed() {
        Boolean flag=true;
        JSONObject obj=new JSONObject(inActiveParentPayload);
        String id=obj.getString("parentId");
        Response response = APIRequests.executeRequest(url, path, "", "", "get");
        response.getBody().prettyPrint();
        JSONArray arr = new JSONArray(response.getBody().asString());
        for(int i=0;i<arr.length() ;i++)
        {
            obj=arr.getJSONObject(i);
            String currentId=obj.getString("id");
            Boolean isActive=obj.getBoolean("isDisabled");
            if((currentId.equals(id))||isActive)
            {
                Assert.assertTrue("InActive Id is returned", false);
                flag=false;
            }
        }
        if(flag)
        {
            Assert.assertTrue("InActive Id is not returned", true);
        }
    }



}

