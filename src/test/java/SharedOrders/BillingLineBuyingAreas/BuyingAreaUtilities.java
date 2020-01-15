package SharedOrders.BillingLineBuyingAreas;

import apirequests.APIRequests;
import io.restassured.response.Response;
import org.json.JSONObject;

public class BuyingAreaUtilities {
    public static JSONObject createParentBuyingArea(String payload,String url,String path)
    {
        Response parentResponse = APIRequests.executeRequest(url, path, payload, "", "Post");
        parentResponse.getBody().prettyPrint();
        JSONObject parentObj = new JSONObject(parentResponse.getBody().asString());
        //body to create  child buyingarea
        String parentId = parentObj.getString("id");
        parentObj.remove("id");
        parentObj.put("parentId", parentId);
        return parentObj;
    }

}
