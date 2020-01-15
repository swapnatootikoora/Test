package apirequests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import utilities.Utilities;

import static io.restassured.RestAssured.given;

public class APIRequests {
    public static Response getAPI(String url) {
        Response response = RestAssured.given()
                .contentType("application/json").accept(ContentType.JSON)
                .when()
                .get(url)
                .then().extract().response();
                return response;


    }


    public static Response PostAPI(String url, String payload) {
        Response response = RestAssured.given()
                .contentType("application/json").accept(ContentType.JSON).body(payload)
                .when().post(url).then().extract().response();
              return response;


    }


    public static Response putAPI(String url, String payload) {
        Response response = RestAssured.given()
                .contentType("application/json").accept(ContentType.JSON).body(payload)
                .when().post(url).then().extract().response();
        return response;


    }


    public static Response executeRequest(String uri,String path,String payload,String auth,String method)

    {
        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri(uri);
        requestBuilder.setBasePath(path);

        if(!auth.isEmpty())
        {
            requestBuilder.addHeader("Authorization",auth);
        }

        if (method.equalsIgnoreCase("post") || method.equalsIgnoreCase("put")) {

            requestBuilder.setBody(payload);

        }

        requestBuilder.addHeader("Content-Type", "application/json");
        requestBuilder.addHeader("accept", "application/json");
        RequestSpecification requestSpec = requestBuilder.build();

        return execute(method,requestSpec);

    }


    public static Response execute(String method,RequestSpecification requestSpec) {

         Response response = given().relaxedHTTPSValidation().spec(requestSpec).request(method);
        return response;

    }

    public static Response executeRequestWithParams(String uri,String path,String body,String auth,String queryParam,String method)

    {
        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri(uri);
        requestBuilder.setBasePath(path);

        if(!auth.isEmpty())
        {
            requestBuilder.addHeader("Authorization",auth);
        }

        if (method.equalsIgnoreCase("post") || method.equalsIgnoreCase("put")) {
            requestBuilder.setBody(body);
        }
        requestBuilder.addHeader("Content-Type", "application/json");
        requestBuilder.addHeader("accept", "application/json");
        if(queryParam!= null && !queryParam.isEmpty()) {
            JSONObject queryParamObj = new JSONObject(queryParam);
            requestBuilder.addQueryParams(queryParamObj.toMap());
        }
        RequestSpecification requestSpec = requestBuilder.build();
        return execute(method,requestSpec);

    }
public static String payloadFromFile(String fileName)
{
    String filePath = Utilities.buildFilePath("BillingLines/",fileName);
    String payload= Utilities.readFile(filePath);

    return payload;
}
    public static JSONObject payloadObjFromFile(String fileName)
    {
        String filePath = Utilities.buildFilePath("BillingLines/",fileName);
        String payload= Utilities.readFile(filePath);
JSONObject obj=new JSONObject(payload);
        return obj;
    }

public static String getValueFromResponse(Response response,String key)
{
    JSONObject obj=new JSONObject(response.getBody().asString());
    String value=obj.getString(key);
    return value;
}
public static JSONObject replaceValue(JSONObject obj,String key,String value)
{
    obj.remove(key);
    obj.put(key,value);
    return obj;
}

    public static JSONObject replaceArrayValue(JSONObject obj,String key,String value)
    {
        obj.remove(key);
        String[] val={value};
        obj.put(key,val);
        return obj;
    }


}


