package utilities;

import apirequests.APIRequests;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class EnviornmentDetails {

    public static final String dev_url="https://gcodev.global.com";
    public static final String test_url="https://gcotest.global.com";
    public static final String intRBE_url = "https://gssoniqavaildev.global.com:961/IntBuild";
    public static final String testRBE_url = "https://gssoniqavailtest.global.com:963/Test";
    public static final String devgSchedule_url = "https://gscheduledev.thisisglobal.com";
    public static  final String testgSchedule_url = "https://gscheduletest.thisisglobal.com";
    public static String url;
    public static String RBEUrl;
    public static String gScheduleUrl;

    public static void envDetails(String env)
    {
        if(env.equalsIgnoreCase("dev"))
        {
            url=dev_url;
            //RBEUrl=devRBE_url;
            gScheduleUrl=devgSchedule_url;
        }
        else if(env.equalsIgnoreCase("test"))
        {
            url=test_url;
            RBEUrl=testRBE_url;
            gScheduleUrl=testgSchedule_url;
        }
        else if(env.equalsIgnoreCase("int"))
        {

            RBEUrl=intRBE_url;

        }
    }
    public static List<String> getEnvVariables(String env) {
        List<String> variables = new ArrayList<String>();
        String product_url = "https://gco"+env+".global.com/billinglineproducts/api/products";
        variables.add(product_url);
        String buyingareas_url = "https://gco" + env + ".global.com/billinglinebuyingareas/api/billinglinebuyingareas";
        variables.add(buyingareas_url);
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"testdata/data.json";
        variables.add(filePath);
        return variables;
    }
    public static String getValidBuyingArea(List<String> variables) {
        Response response = APIRequests.executeRequestWithParams(variables.get(1),"","","","","get");
        JSONArray arr=new JSONArray(response.getBody().asString());
        JSONObject obj=arr.getJSONObject(0);
        String validId=obj.getString("id");
        return validId;
    }


    public static List<String> getBLEnvVariables(String env) {
        List<String> variables = new ArrayList<String>();
        String billingLineItems_url = "https://gco"+env+".global.com/billinglineitems/api/BillingLineItems";
        variables.add(billingLineItems_url);
        String buyingareas_url = "https://gco" + env + ".global.com/billinglinebuyingareas/api/billinglinebuyingareas";
        variables.add(buyingareas_url);
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"testdata/data.json";
        variables.add(filePath);
        return variables;
    }


}
