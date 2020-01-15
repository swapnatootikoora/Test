package utilities;

import apirequests.APIRequests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class Utilities {
    public  static String url = "";
    public static String ordersPath="orders/api/Orders";
    public static String billinglinePath="billinglineitems/api/BillingLineItems";
    public static String plannerPath="people/api/People?Capability=Planner";
    public static String productPath="billinglineproducts/api/Products";
    public static String buyingAreaIdPath="billinglinebuyingareas/api/BillingLineBuyingAreas";
    public static String clientPath="customers/api/Clients";
    public static String contactPath="customers/api/Clients/a4e2aac0-eddb-4ded-a9e5-aa9901188603/contacts";
    public static String ownerPath="people/api/People?Capability=Owner";
    public  static String orderItems="orderItems";
    private static String fileSep = System.getProperty("file.separator");
private static Path path=Paths.get("");

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String buildFilePath(String foldername, String filename){

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        //String path=System.getProperty("user.dir");
        System.out.println("Current relative path is: " + s);
        System.out.println("foloder name is : "+ foldername);
        String filePath = s + "/testdata/"+foldername + filename;
        return filePath;
    }
    public static String getJsonValue(String jsonContent,String key)
    {
        JSONObject obj = new JSONObject(jsonContent);
        return obj.getString(key);
    }
    public static boolean getJsonBooleanValue(String jsonContent,String key)
    {
        JSONObject obj = new JSONObject(jsonContent);
        return obj.getBoolean(key);
    }

    public static String getJsonValue(JSONObject obj,String key)
    {
        //JSONObject obj = new JSONObject(jsonContent);
        return obj.getString(key);
    }
    public static String getErrorMessage(String body)
    {
        JSONObject obj=new JSONObject(body);
        JSONObject obj1=obj.getJSONObject("errors");
        Iterator<String> keys = obj1.keys();
        String key = keys.next();
        JSONArray arr=obj1.getJSONArray(key);
        String temp=arr.getString(0);
        if(temp.contains(","))
        {
            String Final=temp.substring(0,temp.indexOf(','));
            return Final;
        }


        return temp;
    }

   /* public static String getRBEErrorMessage(String body)
    {
        JSONObject obj=new JSONObject(body);
        JSONObject obj1=obj.getJSONObject("Error");
        Iterator<String> keys = obj1.keys();
        String key = keys.next();
        //JSONArray arr=obj1.getJSONArray(key);
        //String temp=arr.getString(0);
        if(temp.contains(","))
        {
            String Final=temp.substring(0,temp.indexOf(','));
            return Final;
        }


        return temp;
    }
*/




    public static Boolean validateJsonAgainstSchema(JSONObject configSchema,JSONObject responseObj)
    {
        Boolean flag=false;
        Set<String> schemaKeys = configSchema.keySet();
        Set<String> responseKeys = responseObj.keySet();
        //comparing the keys set
        if (schemaKeys.equals(responseKeys)) {
            flag=true;

        } else {
            return false;
        }
        Iterator schemaKey =schemaKeys.iterator();
        Iterator responseKey =responseKeys.iterator();
        while(schemaKey.hasNext())
        {
            Object temp = configSchema.get(schemaKey.next().toString());
            Object temp1=responseObj.get(responseKey.next().toString());
            //Comparing the data types
            if(temp1.getClass().getName().equals(temp.getClass().getName()))
            {
                flag=true;
            }
            else
            {
                return false;
            }
        }
        return flag;
    }

    public static String getMessage(String body)
    {
        JSONObject obj=new JSONObject(body);
        JSONObject obj1=obj.getJSONObject("message");
        Iterator<String> keys = obj1.keys();
        String key = keys.next();
        JSONArray arr=obj1.getJSONArray(key);
        String temp=arr.getString(0);
        if(temp.contains(","))
        {
            String Final=temp.substring(0,temp.indexOf(','));
            return Final;
        }


        return temp;
    }
    public static String envName()
    {
        String env="";

        String path=System.getProperty("user.dir")+fileSep+"src"+fileSep+"test"+fileSep+"resources"+fileSep+"config"+fileSep+"config.properties";
        InputStream inputStream;

        try {

            inputStream=new FileInputStream(path);
            Properties prop=new Properties();
            prop.load(inputStream);
            env=prop.getProperty("environment");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return env;
    }
public static String getRandomNumber()
{
     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    System.out.println(sdf.format(timestamp));
    String randomNumber=sdf.format(timestamp);
    return randomNumber;
}
/* Create the BillingLines Payload */
public static Response createBillingLines(String url)
{
    JSONObject billingPayload= APIRequests.payloadObjFromFile("CreateBillingLine.json");

    String payload=APIRequests.payloadFromFile("CreateOrder.json");

   //JSONObject obj=new JSONObject(payload);
    // Response response = APIRequests.executeRequest(url, clientPath, "" ,"", "get");
    //String clientId=APIRequests.getValueFromResponse(response,"clientId");
    // payload=APIRequests.replaceValue(payload,"clientId",clientId);

    // Response response = APIRequests.executeRequest(url, contactPath, "", "", "get");
    //String contactIds=APIRequests.getValueFromResponse(response,"contactIds");
    //payload=APIRequests.replaceArrayValue(payload,"contactIds",contactIds);

    // Response response = APIRequests.executeRequest(url, ownerId, "","", "get");

    /*JSONArray arr = response.getJSONArray("capabilities");
    for (int j = 0; j < arr.length(); j++){
        cap.add(arr.getInt(j));
    }
    if(cap.contains(1)&&cap.contains(2))
    {
        Assert.assertTrue(true);
    }*/
    //String ownerIds=APIRequests.getValueFromResponse(response,"OwnerIds")

    //payload=APIRequests.replaceArrayValue(payload,"ownerIds",ownerIds);

    Response response = APIRequests.executeRequest(url, ordersPath, payload, "", "post");
    String orderId=APIRequests.getValueFromResponse(response,"id");
    billingPayload=APIRequests.replaceValue(billingPayload,"orderId",orderId);
    Assert.assertEquals("Check status codes for succesful response  ", 201,response.getStatusCode());
    response.getBody().prettyPrint();


    response = APIRequests.executeRequest(url, buyingAreaIdPath, "", "", "get");
    JSONArray arr=new JSONArray(response.getBody().asString());
    JSONObject obj=arr.getJSONObject(0);
    String buyingAreaId=obj.getString("id");


    payload=APIRequests.payloadFromFile("BillingLineProduct.json");
    obj=new JSONObject(payload);
    obj=APIRequests.replaceArrayValue(obj,"buyingAreaIds",buyingAreaId);
    response = APIRequests.executeRequest(url, productPath, obj.toString(), "", "post");
    String billingLineProduct=APIRequests.getValueFromResponse(response,"id");
    billingPayload=APIRequests.replaceValue(billingPayload,"productId",billingLineProduct);
    Assert.assertEquals("Check status codes for succesful response  ", 201,response.getStatusCode());
    response.getBody().prettyPrint();

    response = APIRequests.executeRequest(url, plannerPath, "", "", "get");
    arr=new JSONArray(response.getBody().asString());
    obj=arr.getJSONObject(0);
    String plannerId=obj.getString("id");
    billingPayload=APIRequests.replaceValue(billingPayload,"plannerId",plannerId);
    Assert.assertEquals("Check status codes for succesful response  ", 200,response.getStatusCode());
    response.getBody().prettyPrint();
    billingPayload=updateJson.updateJsonData(billingPayload,buyingAreaId);


    response = APIRequests.executeRequest(url, billinglinePath, billingPayload.toString(), "", "post");
    response.getBody().prettyPrint();

    return response;
}

    public static String createJsonObject(Object obj){

        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;

    }

    public static void downloadLocally(byte[] pdfFile,String filePath) {
        FileOutputStream fos;
        try {

            fos = new FileOutputStream(filePath);
            fos.write(pdfFile);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}




