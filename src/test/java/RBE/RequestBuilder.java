package RBE;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.activation.DataHandler;

import static io.restassured.RestAssured.given;

public class RequestBuilder {


    public static RequestSpecBuilder requestBuilder = null;
    public static RequestSpecification requestSpec;
    public static Response response;

    public static void buildRequest(String uri,String path,String body,String auth,String method)

    {

        requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri(uri);
        requestBuilder.setBasePath(path);
        requestBuilder.addHeader("Authorization",auth);

       /* if (!(DataHandler.getQueryParamkey() == null || DataHandler.getQueryParamvalue() == null)) {
            if (!(DataHandler.getQueryParamkey().isEmpty() || DataHandler.getQueryParamvalue().isEmpty())) {
                AddQueryParams(DataHandler.getQueryParamkey(), DataHandler.getQueryParamvalue());
            }
        }*/
        if (method.equalsIgnoreCase("post") || method.equalsIgnoreCase("put")) {

            requestBuilder.setBody(body);
            requestBuilder.addHeader("Content-Type", "application/json");
            requestBuilder.addHeader("accept", "application/json");
        }

        requestSpec = requestBuilder.build();
        ExecuteRequest(method);

    }



    public static void ExecuteRequest(String method) {
        response = given().relaxedHTTPSValidation().spec(requestSpec).request(method);
        System.out.println(response.statusCode());
        //System.out.println(response.body().print());

        String body = response.body().asString();
        System.out.println(body);
        //jsonPath = new JsonPath(Body);
    }



}
