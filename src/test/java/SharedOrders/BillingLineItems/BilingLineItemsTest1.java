package SharedOrders.BillingLineItems;

import apirequests.APIRequests;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jsonClasses.Billinglines;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import utilities.Utilities;

import java.util.Arrays;
import java.util.ArrayList;

public class BilingLineItemsTest1 {
    String path = "https://gcotest.global.com/billingline/api/BillingLines";



    /*This test is to create a new BillingLineItems using the API : POST*/
    @Test
    public void createBillingLineTestHappyPath() {

        Billinglines BillingLineObj = new Billinglines();

        BillingLineObj.setOrderId("b1375915-6c3d-4df5-aac2-aaf400e3ebab");
        BillingLineObj.setPlannerId("90C44CAE-2A4A-4B5D-A5EC-AA9500A6C839");
        BillingLineObj.setProductId("3B50FC9A-26B1-42C7-A7B4-AACB0084B9E4");
        BillingLineObj.setStartMonth("2019-11-30");
        BillingLineObj.setDurationInMonths(2);

       // final JSONArray arr = new JSONArray();

        /*for(int i = 0 ; i< list.size() ; i++) {
            final JSONObject obj = new JSONObject();
            p = list.get(i);
            obj.add("id", p.getId());
            arr.add(obj);
        }


        for(int i = 0 ; i< list.size() ; i++) {
            final JSONObject obj = new JSONObject();
            p = list.get(i);
            obj.add("date", new MyDateFormatter().getStringFromDateDifference(p.getCreationDate()));
            obj.add("value", p.getValue());
        }*/

        BillingLineObj.setBillingLineBuyingAreaRevenues(Arrays.asList(new String[]{"id","8798f1f5-cd1b-455e-b44a-aacb00845536"}));
        BillingLineObj.setMonthBuyingAreaRevenues(Arrays.asList(new String[]{"date","2019-11-30"}));
        //BillingLineObj.setValue(444);
       BillingLineObj.setRevenue(444);
        String json = Utilities.createJsonObject(BillingLineObj);
        System.out.println(">>>>>>>\n\n\n" + json);

        Response response = APIRequests.PostAPI(path, json);
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }

}
