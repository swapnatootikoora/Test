package SharedOrders.BillingLineItems;

import SharedOrders.BillingLineBuyingAreas.BuyingAreaUtilities;
import SharedOrders.DataLayer.DatabaseOperations;
import SharedOrders.DataLayer.SqlSeverDriver;
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
import utilities.Utilities;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class BillingLineItemsTest {


    //private final static String url = "https://gcotest.global.com/billingline/api/BillingLines";
    public static String url = "";
    private final static String path = "billinglineitems/api/BillingLineItems";
    private final static String configUrl = "https://gcotest.global.com";
    private final static String configPath = "billinglineitems/api/Configuration";
    private final static String ordersUrl = "https://gcotest.global.com";
    private final static String ordersUrlPath = "orders/api/Orders";
    private static String fileSep = System.getProperty("file.separator");
    public static EnvDetails envDetails = new EnvDetails();
    public String orderItems = "orderItems";

    /* setting up the env... details*/
    @BeforeClass
    public static void setUp() {
        String env = Utilities.envName();
        envDetails.envDetails(env);
        System.out.println("this is the ::" + env);
        url = envDetails.url;
        System.out.println("this is the url ::" + url);
    }

    /*This returns all the SharedOrders.BillingLines and then fetches a particular billing lines by id*/
    @Test
    public void getAllBillingLines() {
        Response response = APIRequests.executeRequest(url, path, "", "", "get");
        String responseBody = response.asString();
        System.out.println(" Get All the  Billing line Items details \n\n");
        response.getBody().prettyPrint();
        JSONArray jsonResponse = new JSONArray(response.asString());
        String id = jsonResponse.getJSONObject(0).getString("id");
        String currentPath = path + fileSep + id;
        response = APIRequests.executeRequest(url, currentPath, "", "", "get");
        System.out.println(" Get the  Billing line Items details \n\n");
        response.getBody().prettyPrint();

    }

    /*This test is to verify that Billing Line Instance can be created when a valid planner id and Product id are provided and saves it to database.*/
    /*for order id go to https://gcotest.global.com/orders/swagger/index.html*/
    /* for planner id go to https://gcotest.global.com/people/swagger/index.html,https://gcotest.global.com/people/api/People?Capability=Planner*/
    /* for product id go to https://gcotest.global.com/billinglineproducts/swagger/index.html*/
    /* This it test is to verify that Billinglineitems is updated in the orders API */
    @Test
    public void createBillingLineInstance() throws IOException {
        Response response = Utilities.createBillingLines(url);
        /* This it test is to verify that Billinglineitems is updated in the orders API */
        String billingLineId = APIRequests.getValueFromResponse(response, "id");
        String orderId = APIRequests.getValueFromResponse(response, "orderId");
        String currentPath = ordersUrlPath + "/" + orderId + "/" + orderItems;
        Response response1 = APIRequests.executeRequest(url, currentPath, "", "", "get");
        System.out.println(" The orderItems by OrderId: \n\n");
        response1.getBody().prettyPrint();
        JSONArray jsonArray = new JSONArray(response1.getBody().asString());
        Boolean flag = false;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj1 = jsonArray.getJSONObject(i);
            String temp = Utilities.getJsonValue(obj1, "externalId");
            if (temp.equals(billingLineId)) {
                flag = true;
                Assert.assertTrue(true);
                System.out.println(temp);

                break;
            }

        }
        if (!flag) {
            Assert.assertTrue("expected billingline is not present under that order", false);
        }


    }

    /*This test is to verify that an Billing Line Instance  can't be created when Planner id and Product id are Null */

    @Test
    public void NullPlanner() throws IOException {

        String filename = "NullPlanner.json";
        String filePath = Utilities.buildFilePath("BillingLineItems/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, "", "post");
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("Billing Line Id: \n" + jsonPath.getString("id"));
        System.out.println("Billing Line Name : \n" + jsonPath.getString("name"));
        System.out.println("Planner Id: \n" + jsonPath.getString("plannerId"));
        System.out.println("Product Id : \n" + jsonPath.getString("productId"));
        String responseBody = response.asString();
        System.out.println("The order can't be created :\n" + responseBody);
        Assert.assertEquals("This is a failure as the status codes don't match\n", 400, response.getStatusCode());
        String errorMsg = "";
        if (response.getBody().asString().contains("errors")) {
            errorMsg = Utilities.getErrorMessage(response.getBody().asString());

        }
        System.out.println(errorMsg);
    }


    /*This test is to verify that an Billing Line Instance  can't be created when Planner id and Product id are Null */

    @Test
    public void NullProduct() throws IOException {

        String filename = "NullProduct.json";
        String filePath = Utilities.buildFilePath("BillingLineItems/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, "", "post");
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("Billing Line Id: \n" + jsonPath.getString("id"));
        System.out.println("Billing Line Name : \n" + jsonPath.getString("name"));
        System.out.println("Planner Id: \n" + jsonPath.getString("plannerId"));
        System.out.println("Product Id : \n" + jsonPath.getString("productId"));
        System.out.println("The order can't be created :\n\n");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 400, response.getStatusCode());
        String errorMsg = "";
        if (response.getBody().asString().contains("errors")) {
            errorMsg = Utilities.getErrorMessage(response.getBody().asString());

        }
        System.out.println(errorMsg);
    }


    /*This test is to verify that billing Line Instance can't be created when Duration is out of range  */

    @Test
    public void DurationOutOfRange() {

        String filename = "DurationIsOutOfRange.json";
        String filePath = Utilities.buildFilePath("BillingLineItems/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, "", "post");
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("Billing Line Id : \n" + jsonPath.getString("id"));
        System.out.println("Billing Line Name: \n" + jsonPath.getString("name"));
        System.out.println("The order can't be created :\n\n");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 400, response.getStatusCode());
        String errorMsg = "";
        if (response.getBody().asString().contains("errors")) {
            errorMsg = Utilities.getErrorMessage(response.getBody().asString());

        }
        System.out.println(errorMsg);
    }


    /*Get billing line by id*/
    @Test
    public void getBillingLineById() {
        Response response = Utilities.createBillingLines(url);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String billingLineId = jsonPath.getString("id");
        String currentPath = path + fileSep + billingLineId;
        response = APIRequests.executeRequest(url, currentPath, "", "", "get");
        System.out.println("Response body: \n\n");
        response.getBody().prettyPrint();
    }

    /*This test returns a 404 :NOT FOUND message , when  BillingLine id doesn't exists*/
    @Test
    public void invalidBillingLineIdTest() {
        String invalidBillingLineId = "6dd8470b-3e6b-4161-93b4-aab900997398";
        String currentPath = path + fileSep + invalidBillingLineId;
        Response response = APIRequests.executeRequest(url, currentPath, "", "", "get");
        System.out.println("status code : " + response.getStatusCode());
        System.out.println(" Response Body \n\n");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 404, response.getStatusCode());
        String errorMsg = "";
        if (response.getBody().asString().contains("errors")) {
            errorMsg = Utilities.getErrorMessage(response.getBody().asString());
        }
        System.out.println(errorMsg);
    }

    /* Creates a new billing-line instance and saves it to database,prints the Startmonth by giving the billingline id in the sql query */
   /* @Test
    public void getTest() throws IOException {
        Response response = Utilities.createBillingLines(url);
        System.out.println(" Billing line Items details \n\n");
        response.getBody().prettyPrint();
        String filename = "BillingLines.json";
        String filePath = Utilities.buildFilePath("BillingLineItems/", filename);
        String payload = Utilities.readFile(filePath);
         response = APIRequests.executeRequest(url, path, payload, "", "post");
        response.getBody().prettyPrint();
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String Id = jsonPath.getString("id");
        String sql = "SELECT * from billingline.SharedOrders.BillingLines";
         sql = "SELECT StartMonth from billingline.BillingLineItems where Id = '" + Id + "'";
        System.out.println(sql);
        Connection conn = SqlSeverDriver.connectToDB();
        List<List<String>> data = new ArrayList<List<String>>();
        data = DatabaseOperations.fetchResults(conn, sql);
        System.out.println(data);

    }*/

    /*This test returns a 400 :NOT FOUND message , when  Product id is set to isDisabled=true */
    @Test
    public void DisabledProduct() throws IOException {
        //Response response=Utilities.createBillingLines(url);
        String filename = "DisabledProduct.json";
        String filePath = Utilities.buildFilePath("BillingLineItems/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, "", "post");
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("Billing Line Id: \n" + jsonPath.getString("id"));
        System.out.println("Planner Id: \n" + jsonPath.getString("plannerId"));
        System.out.println("Product Id : \n" + jsonPath.getString("productId"));
        System.out.println("The billing line  can't be created :\n\n");
        response.getBody().prettyPrint();
        Assert.assertEquals("This is a failure as the status codes don't match\n", 400, response.getStatusCode());

    }

    /* Validating the error message when order id is invalid or not in correct format to create the billing lines */

    @Test
    public void InvalidOrderId() throws IOException {
       String invalidOrderId="";
        String filename = "BillingLines.json";
        String filePath = Utilities.buildFilePath("BillingLineItems/", filename);
        String payload = Utilities.readFile(filePath);
        JSONObject obj = new JSONObject(payload);
        obj.remove("orderId");
        obj.put("orderId", invalidOrderId);
         payload = obj.toString();
        Response response = APIRequests.executeRequest(url, path, payload, "", "post");
        String errorMsg = "";
        if (response.getBody().asString().contains("errors")) {
            errorMsg = Utilities.getErrorMessage(response.getBody().asString());

        }
        String errorMsg1 = errorMsg;
        String errMsg_notFound = "Order Id not found.";
        String errMsg_invalidFormat = "Error converting value \"" + invalidOrderId + "\" to type 'System.Guid'. Path 'orderId'";
        Assert.assertEquals("Check error message for non exisiting ID  ", errMsg_invalidFormat, errorMsg1);

    }

    /* This is to return the configuration variables */

    @Test
    public void getConfigurationTest() {
        Boolean flag = false;
        Response response = APIRequests.executeRequest(configUrl, configPath, "", "", "get");
        Assert.assertEquals("Validating the response code", 200, response.getStatusCode());
        JSONObject responseObj = new JSONObject(response.getBody().asString());
        String filename = "BillingLineItemsConfigurationSchema.json";
        String filePath = Utilities.buildFilePath("BillingLineItems/", filename);
        String schema = Utilities.readFile(filePath);
        JSONObject configSchema = new JSONObject(schema);
        flag = Utilities.validateJsonAgainstSchema(configSchema, responseObj);
        if (flag) {
            Assert.assertTrue("The Response is adhering to the expected json schema", true);
        } else {
            Assert.assertTrue("The Response is not adhering to the expected json schema", false);
        }
        System.out.println("The Response body is : \n\n");
        response.getBody().prettyPrint();
    }

    // This test is to verify the invalid path handling
    @Test
    public void invalidPathConfigTest() {
        String env = "test";
        Response response = APIRequests.executeRequest(configUrl, "", "", "", "get");
        Assert.assertEquals("Validating the response code for invalid path ", 404, response.getStatusCode());
        System.out.println("The Response body is : \n\n");
        response.getBody().prettyPrint();
        String errorMsg = "";
        if (response.getBody().asString().contains("errors")) {
            errorMsg = Utilities.getErrorMessage(response.getBody().asString());

        }
        System.out.println(errorMsg);
    }

    /*This test is to verify that an Billing Line Instance  can't be created when Negative values entered for Revenue */

    @Test
    public void NegativeRevenue() throws IOException {

        String filename = "NegativeRevenue.json";
        String filePath = Utilities.buildFilePath("BillingLineItems/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.executeRequest(url, path, payload, "", "post");
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String responseBody = response.asString();
        System.out.println("The order can't be created :\n" + responseBody);
        Assert.assertEquals("This is a failure as the revenue can't be negative", 400, response.getStatusCode());
        String errorMsg = "";
        if (response.getBody().asString().contains("errors")) {
            errorMsg = Utilities.getErrorMessage(response.getBody().asString());

        }
        System.out.println(errorMsg);
    }


    @Test
    public void createBillingLineInstanceTest() throws IOException {

        Response response = Utilities.createBillingLines(url);
        System.out.println(" Billing line Items details \n\n");
        response.getBody().prettyPrint();

    }
}





