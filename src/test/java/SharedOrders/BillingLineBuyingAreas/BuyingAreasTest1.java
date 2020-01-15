package SharedOrders.BillingLineBuyingAreas;

import apirequests.APIRequests;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jsonClasses.Billinglines;
import jsonClasses.billingLineBuyingAreas;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.EnvDetails;
import utilities.Utilities;

import java.util.Arrays;

public class BuyingAreasTest1 {

    //String path = "https://gcotest.global.com/billinglinebuyingareas/api/BillingLineBuyingAreas";
    public static String url = "";
    private final static String path = "billinglinebuyingareas/api/BillingLineBuyingAreas";

    public static EnvDetails envDetails1 = new EnvDetails();
    billingLineBuyingAreas BuyingAreasObj = new billingLineBuyingAreas();

    public static String activeParentPayload = "";
    public static String inActiveParentPayload = "";

    @BeforeClass
    public static void loadTestData() {
        String env = Utilities.envName();
        envDetails1.envDetails(env);
        System.out.println("Buyingarea tests starting........environment:  " +env);

        url=envDetails1.url;
        System.out.println(url);



    }


    /* Creating Active and Inactive ParentIDs and Payloads */
    @Before
    public void getParentBuyingAreaIds()
    {
        BuyingAreasObj.setParentId("");
        BuyingAreasObj.setName("test");
        BuyingAreasObj.setEndDate("2019-11-16T05:40:29.283Z");
        BuyingAreasObj.setAvailableForBillingLine("true");
        BuyingAreasObj.setIsDisabled(false);
        String payload = Utilities.createJsonObject(BuyingAreasObj);
        System.out.println(">>>>>>>\n\n\n" + payload);
        JSONObject obj = BuyingAreaUtilities.createParentBuyingArea(payload, url, path);
        activeParentPayload = obj.toString();
        JSONObject obj1 = new JSONObject(payload);
        obj1.put("isDisabled", true);
        payload = obj1.toString();
        obj = BuyingAreaUtilities.createParentBuyingArea(payload, url, path);
        inActiveParentPayload = obj.toString();
    }

    /*This test is to get all billingLineBuyingAreas  using the GET : POST*/
    @Test
    public void getAllBuyingAreasTest() {

        //Response response = APIRequests.getAPI(path);
        Response response=APIRequests.executeRequest(url,path,"","","Get");
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }


    /*This test is to create  Active ParentBuyingArea Id  using the API : POST*/
    @Test
    public void createActiveParentBuyingAreasTest() {

        Response response=APIRequests.executeRequest(url,path,activeParentPayload,"","Post");
        //Response response = APIRequests.PostAPI(path, json);
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 201,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }

    /*This test is to create a Active ChildBuyingArea under Active ParentBuyingArea  using the API : POST*/
    @Test
    public void createActiveChildBuyingAreasTest() {
        Response response=APIRequests.executeRequest(url,path,activeParentPayload,"","Post");
       // Response response = APIRequests.PostAPI(path, json);
        response.getBody().prettyPrint();
        JSONObject obj = new JSONObject(response.getBody().asString());
        String id=obj.getString("id");
        //String parentId=obj.getString("parentId");
        obj.put("parentId", id);
        String payload = obj.toString();
         response=APIRequests.executeRequest(url,path,payload,"","Post");
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 201,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }


    /*This test is to get  billingLineBuyingArea by given id  using the GET : POST*/
    @Test
    public void getSpecificBuyingAreasTest() {
        String CurrentPath=path+"/"+"5eddc192-8088-4a1b-9b9d-aafe00ccd596";
       // Response response = APIRequests.getAPI(path+"/"+CurrentPath);
        Response response=APIRequests.executeRequest(url,CurrentPath,"","","Get");
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }




    /*This test is to update a billingLineBuyingArea by API : PUT
    @Test
    public void updateBuyingAreaTest() {


        Response response = APIRequests.getAPI(path);
        JSONArray jsonResponse = new JSONArray(response.asString());
        Assert.assertTrue("If there are no activebuyingarea present, test terminates", jsonResponse.length() > 0);
        String id = jsonResponse.getJSONObject(3).getString("id");

        String parentId = jsonResponse.getJSONObject(3).getString("parentId");
        String name = jsonResponse.getJSONObject(3).getString("name");
        String endDate = jsonResponse.getJSONObject(3).getString("endDate");
        String availableForBillingLine = jsonResponse.getJSONObject(3).getString("availableForBillingLine");
        boolean isDisabled = jsonResponse.getJSONObject(3).getBoolean("isDisabled");


        billingLineBuyingAreas BuyingAreasObj = new billingLineBuyingAreas();
        BuyingAreasObj.setId(id);
        if(parentId!=null)
        {
            parentId=parentId;
        }else
        {
            parentId="b196f26f-141a-4050-a427-aafe00c6672e";
        }
        BuyingAreasObj.setParentId(parentId);
        BuyingAreasObj.setName(name);
        if(endDate!=null)
        {
            endDate=endDate;
        }else
        {
            endDate="2019-11-16T05:40:29.283";
        }
        BuyingAreasObj.setEndDate(endDate);
        BuyingAreasObj.setAvailableForBillingLine(availableForBillingLine);
        //String json = Utilities.createJsonObject(BuyingAreasObj);
        if (isDisabled) {
            isDisabled = false;
        } else {
            isDisabled = true;
        }
        //System.out.println(">>>>>>>\n\n\n" + json);
        BuyingAreasObj.setIsDisabled(isDisabled);
        String json = Utilities.createJsonObject(BuyingAreasObj);

        // response = APIRequests.executeRequest(url, parentPath, payload, "", "put");
        response.getBody().prettyPrint();
        response = APIRequests.putAPI(path + "/" + id, json);
        Assert.assertEquals("Check status codes for successful response  ", response.getStatusCode(), HttpStatus.SC_OK);
        //jsonResponse = new JSONArray(response.asString());
        JSONObject jsonObject = new JSONObject(response.asString());
        System.out.println(">>>> \n\n\n" + jsonObject.toString());
        System.out.println("<<<<<\n" + jsonObject.getBoolean("isDisabled"));
        Assert.assertTrue("Update failed.", isDisabled == jsonObject.getBoolean("isDisabled"));
        System.out.println("Update succeeded.");


    }*/



/* This test is to update the Buying area active to inactive using by PUT request*/

    @Test
    public void updateBuyingareaTest()
    {
        billingLineBuyingAreas BuyingAreasObj = new billingLineBuyingAreas();

        BuyingAreasObj.setParentId("");
        BuyingAreasObj.setName("test");
        BuyingAreasObj.setEndDate("2019-11-16T05:40:29.283Z");
        BuyingAreasObj.setAvailableForBillingLine("true");
        BuyingAreasObj.setIsDisabled(false);

        String json = Utilities.createJsonObject(BuyingAreasObj);
        System.out.println(">>>>>>>\n\n\n" + json);
        Response response=APIRequests.executeRequest(url,path,json,"","Post");
       // Response response = APIRequests.PostAPI(path, json);
        response.getBody().prettyPrint();

        String parentId=Utilities.getJsonValue(response.getBody().asString(),"id");
        String currentPath=path+"/"+parentId;
        JSONObject obj=new JSONObject(response.getBody().asString());
        obj.put("isDisabled",true);
        String payload=obj.toString();
        response = APIRequests.executeRequest(url, currentPath, payload, "", "put");
        System.out.println("The product details are : \n\n");
        response.getBody().prettyPrint();

    }


    }
