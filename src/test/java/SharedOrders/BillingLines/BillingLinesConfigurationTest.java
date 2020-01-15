package SharedOrders.BillingLines;

import apirequests.APIRequests;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.EnvDetails;
import utilities.Utilities;

public class BillingLinesConfigurationTest {


    public  static String url = "";
    private final static String path = "billinglines/api/Configuration";
    String ordersUrlPath="orders/api/Orders";
    public String orderItems="orderItems";
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

    /* This is to return the configuration variables */

    @Test
    public void getConfigurationTest() {
        Boolean flag=false;
        Response response = APIRequests.executeRequest(url, path, "", "", "get");
        Assert.assertEquals("Validating the response code", 200, response.getStatusCode());
        JSONObject responseObj = new JSONObject(response.getBody().asString());
        String filename = "BillingLinesConfigurationSchema.json";
        String filePath = Utilities.buildFilePath("BillingLines/", filename);
        String schema = Utilities.readFile(filePath);
        JSONObject configSchema = new JSONObject(schema);
        flag=Utilities.validateJsonAgainstSchema(configSchema,responseObj);
        if(flag)
        {
            Assert.assertTrue("The Response is adhering to the expected json schema", true);
        }
        else
        {
            Assert.assertTrue("The Response is not adhering to the expected json schema", false);
        }
        System.out.println("The Response body is : \n\n");
        response.getBody().prettyPrint();
    }

    // This test is to verify the invalid path handling
    @Test
    public void invalidPathConfigTest() {
        String env = "test";
        Response response = APIRequests.executeRequest(url, "", "", "", "get");
        Assert.assertEquals("Validating the response code for invalid path ", 404, response.getStatusCode());
        System.out.println("The Response body is : \n\n");
        response.getBody().prettyPrint();
    }

    // This test is to verify the invalid method handling
    @Test
    public void invalidMethodConfigTest() {
        String env = "test";
        Response response = APIRequests.executeRequest(url, path, "", "", "post");
        Assert.assertEquals("Validating the response code for invalid path ", 405, response.getStatusCode());
        System.out.println("The Response body is : \n\n");
        response.getBody().prettyPrint();
    }


    // This test is to create the orderid, withthat order id create the billingline, go to order API using billing line id to verify the billingline is created or not
/*@Test
    public void endtoEndTest()
    {
        //String randomNumber=Utilities.getRandomNumber();
        Response response=Utilities.createBillingLines(url);
        String billingLineId=APIRequests.getValueFromResponse(response,"id");
         String orderId = APIRequests.getValueFromResponse(response,"orderId");
        String currentPath = ordersUrlPath + fileSep + orderId+fileSep+orderItems;
        Response response1 = APIRequests.executeRequest(url, currentPath, "", "", "get");
        System.out.println(" The orderItems by OrderId: \n\n");
        response1.getBody().prettyPrint();

        JSONArray jsonArray = new JSONArray(response1.getBody().asString());
        Boolean flag=false;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj1 = jsonArray.getJSONObject(i);
            String temp = Utilities.getJsonValue(obj1, "externalId");
            if (temp.equals(billingLineId)) {
                flag=true;
                Assert.assertTrue(true);
                System.out.println(temp);

                break;
            }

        }
        if(!flag)
        {
            Assert.assertTrue("expected billingline is not present under that order",false);
        }

    }*/

}


