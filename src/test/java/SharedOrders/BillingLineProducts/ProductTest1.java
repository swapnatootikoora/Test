package SharedOrders.BillingLineProducts;

import apirequests.APIRequests;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jsonClasses.BillingLineProducts;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.EnvDetails;
import utilities.Utilities;

import java.util.Arrays;
import java.util.List;

public class ProductTest1 {

    public static String url = "";
    private final static String path = "billinglineproducts/api/Products";
            //"https://gcotest.global.com/billinglineproducts/api/Products";
    public static EnvDetails envDetails1 = new EnvDetails();
    public static  BillingLineProducts products=new BillingLineProducts();
    public static String validBuyingId="";
    public static String createProductPayload="";
    /* setting up the env... details*/
    @BeforeClass
    public static void loadTestData() {
        String env = Utilities.envName();
        envDetails1.envDetails(env);
        System.out.println("Product tests starting........environment:  " +env);

        url=envDetails1.url;
        System.out.println(url);

        List<String> variables=envDetails1.getEnvVariables(env);
        validBuyingId=envDetails1.getValidBuyingArea(variables);
        String[] temp={validBuyingId};
        products.setName("testproducts");
        products.setHasCommission(true);
        products.setBuyingAreaIds(Arrays.asList(new String[]{"8798f1f5-cd1b-455e-b44a-aacb00845536"}));
        products.setIsDisabled(false);
        createProductPayload = Utilities.createJsonObject(products);
        System.out.println(">>>>>>>\n\n\n" + createProductPayload);
        JSONObject obj=new JSONObject(createProductPayload);
        obj.remove("buyingAreaIds");
        obj.put("buyingAreaIds",temp);
        createProductPayload=obj.toString();
    }

    /*This test is to get all products  using the GET : GET*/
    @Test
    public void getAllProductTest() {

       // Response response = APIRequests.getAPI(url);
        Response response=APIRequests.executeRequest(url,path,"","","Get");
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }

    /*This test is to create a new Product using the API : POST*/
    @Test
    public void createProduct()
    {
        //Response response = APIRequests.PostAPI(url, json);
        Response response=APIRequests.executeRequest(url,path,createProductPayload,"","Post");
        System.out.println(response.getBody().asString());
        Assert.assertEquals("Check status codes for succesful response  ", 201, response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String name = jsonPath.getString("name");
        String id = jsonPath.getString("id");
        //Assert.assertEquals("The product name doesn't match", "testproduct", name);/*The Assert only gets executed when the expected and actual doesn't match*/
        System.out.println("product name : \n" + name);
        System.out.println("product id : \n" + id);

    }

    /*This test is to get  product by given id  using the GET : GET*/
    @Test
    public void getSpecificProductTest() {
        String CurrentPath=path+"/"+"14422c72-04d5-4a0d-9bc0-ab0401169290";
        //Response response = APIRequests.getAPI(url+"/"+CurrentPath);
        Response response=APIRequests.executeRequest(url,CurrentPath,"","","Get");
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());

    }

    /*This test is to delete  product by given id  using the DELETE : DELETE*/
    @Test
    public void deleteSpecificProductTest() {
        String CurrentPath=path+"/"+"3c98845a-134e-408f-a312-ab0401167162";
        Response response = APIRequests.executeRequest(url, CurrentPath, "", "", "delete");
        Assert.assertEquals(200, response.getStatusCode());
        response.getBody().prettyPrint();
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println(">>>>> " + response.getStatusCode());
    }

    /*This test is to update the product by setting the value isDisabled is false to true*/

    @Test
    public void updateProduct()
    {

        Response response=APIRequests.executeRequest(url,path,createProductPayload,"","Post");
        //Response response = APIRequests.PostAPI(url, json);
        Assert.assertEquals("Check status codes for successful response  ", 201,response.getStatusCode());
        response.getBody().prettyPrint();
        String productId=Utilities.getJsonValue(response.getBody().asString(),"id");
        String currentPath=url+"/"+path+"/"+productId;
        JSONObject obj=new JSONObject(response.getBody().asString());
        obj.put("isDisabled",true);
        String payload=obj.toString();
        response = APIRequests.executeRequest(currentPath, "", payload, "", "put");
        Assert.assertEquals("Check status codes for successful response  ", 200,response.getStatusCode());
        System.out.println("The product details are : \n\n");
        response.getBody().prettyPrint();

    }




}
