package SharedOrders.CampaignItemTypesAPI;

import apirequests.APIRequests;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import utilities.Utilities;

public class CampaignItemTypeTest {
    String path = " https://gcoint.global.com/campaignitemtypes-api/api/CampaignItemTypes";
    private static String fileSep = System.getProperty("file.separator");


    /*This test is to create a campaign item type*/
    @Test
    public void createCampaignItemTypeTest() {

        String filename = "Campaignitemtypes.json";
        String filePath = Utilities.buildFilePath("campaignitemtypes/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.PostAPI(path, payload);
        Assert.assertEquals("Check status codes for successful response  ", response.getStatusCode(), HttpStatus.SC_CREATED);
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String name = jsonPath.getString("name");
        System.out.println("Campaign Item Type name : \n" + jsonPath.getString("name"));
        System.out.println("Campaign Item type ID : \n" + jsonPath.getString("id"));
    }


    /* This test to is verify that the campaign item names need to be unique*/

    @Test
    public void duplicateNametest() {
        String filename = "Campaignitemtypes.json";
        String filePath = Utilities.buildFilePath("campaignitemtypes/", filename);
        String payload = Utilities.readFile(filePath);
        Response response = APIRequests.PostAPI(path, payload);
        Assert.assertEquals("Check status codes for successful response  ", response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String responseBody = response.asString();
        System.out.println("The campaign item type can't be created , as the names are duplicate :\n" + responseBody);
    }

}
