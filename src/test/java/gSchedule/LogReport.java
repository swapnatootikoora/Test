package gSchedule;

import apirequests.APIRequests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.Credentials_gSchedule;
import utilities.EnvDetails;
import utilities.ErrorMsgs;
import utilities.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;



public class LogReport {

    public static String url = "";
    // private final static String url = "https://gscheduletest.thisisglobal.com";
    private final static String testPath="api/LogReporter/Reports";
    private final static String queryParamPath="Logs?request.logGroupId=232&request.fromDate=2019-11-25&request.toDate=2019-11-25&request.sendToEmailList=swapna.tootikoora@global.com";
    private final static String secureListOfLogsPath="api/Core/LogManagement/Log/ListSecure";
    private final static String listOfLogGroupLogsPath="api/Core/LogManagement/Log/ListSecure/607";
    private final static String currentUserLogGroupsPath="api/Core/LogManagement/LogGroup/Administration/ListSecure";
    private final static String path = "api/LogReporter/Reports/Logs";
    private final static String logGroupReportURL = "https://gscheduletest.thisisglobal.com";
    private final static String logGroupReportPath = "api/LogReporter/Reports/Logs?request.logGroupId=232&request.fromDate=2019-11-25&request.toDate=2019-11-25&request.sendToEmailList=swapna.tootikoora@global.com";
    private final static String singleLogReportPath ="api/LogReporter/Reports/Logs?request.logIds=194&request.fromDate=2019-10-29&request.toDate=2019-10-29&request.sendToEmailList=swapna.tootikoora@global.com";
    private final static String LogGroupWithSingleLogReportPath ="api/LogReporter/Reports/Logs?request.logGroupId=303&request.logIds=7&request.fromDate=2019-10-29&request.toDate=2019-10-29&request.sendToEmailList=Swapna.tootikoora@global.com";
    private final static String healthcheckUrl = "https://gscheduletest.thisisglobal.com";
    private final static String healthcheckPath = "api/LogReporter/health/check";
    private static String fileSep = System.getProperty("file.separator");
    private final static String invalidAuthentication = "Bearer fyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6InFhMDBtWlBMTVQtTFVjSG9KT0JIYklWZnJUbyJ9.eyJhdWQiOiJ1cm46Z1NjaGVkdWxlLnRlc3QiLCJpc3MiOiJodHRwOi8vQURGUzIudGhpc2lzZ2xvYmFsLmNvbS9hZGZzL3NlcnZpY2VzL3RydXN0IiwiaWF0IjoxNTY5NTgzODAzLCJleHAiOjE1Njk1ODc0MDMsInVwbiI6IlN3YXBuYS5Ub290aWtvb3JhQGdsb2JhbC5jb20iLCJ1bmlxdWVfbmFtZSI6IlN3YXBuYSBUb290aWtvb3JhIiwiZW1haWwiOiJTd2FwbmEuVG9vdGlrb29yYUBnbG9iYWwuY29tIiwicm9sZSI6WyJTeXN0ZW1BZG1pbiIsIkFjY2VzcyJdLCJncm91cCI6IlRlc3QiLCJhcHB0eXBlIjoiUHVibGljIiwiYXBwaWQiOiJnU2NoZWR1bGUudGVzdCIsImF1dGhtZXRob2QiOiJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6YWM6Y2xhc3NlczpQYXNzd29yZFByb3RlY3RlZFRyYW5zcG9ydCIsImF1dGhfdGltZSI6IjIwMTktMDktMjdUMTE6Mjk6NTguMDgwWiIsInZlciI6IjEuMCJ9.c-PNDOEaoRK1aFCjP5hiUbchR8B45qYW04zpPv5vfsogkQyANXvz0-Ebn6Q8RdVGant5OnIc1wPCleQown8RmjlxS_XYgwUfuFehfc0qIr82FKDTYnCOR9REgjQZZYnimRu8wCR8qICX1hy4icEmTsmzUGeGsfTTgHQSRUk6d556mIds1jER_sxG6Nft-7_n-yqYXKH2Q2L4gYZWryeA23jO0LYaDzUz7vTeHd6PqqXWKrg_A0fiWeb_tE8l8m1wqJdnJztgTJNiud-1AMlEN7-wpDdlKXEFFMTtr3R_Fxp-bNsBohMcKns7x61dn910g-LjAus-0qS1RYq8v6RGVA";
    public static EnvDetails envDetails = new EnvDetails();
    //private final static String validAuth="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6InFhMDBtWlBMTVQtTFVjSG9KT0JIYklWZnJUbyJ9.eyJhdWQiOiJ1cm46Z1NjaGVkdWxlLnRlc3QiLCJpc3MiOiJodHRwOi8vQURGUzIudGhpc2lzZ2xvYmFsLmNvbS9hZGZzL3NlcnZpY2VzL3RydXN0IiwiaWF0IjoxNTczODE4NzMwLCJleHAiOjE1NzM4MjIzMzAsInVwbiI6IlN3YXBuYS5Ub290aWtvb3JhQGdsb2JhbC5jb20iLCJ1bmlxdWVfbmFtZSI6IlN3YXBuYSBUb290aWtvb3JhIiwiZW1haWwiOiJTd2FwbmEuVG9vdGlrb29yYUBnbG9iYWwuY29tIiwicm9sZSI6WyJTeXN0ZW1BZG1pbiIsIkFjY2VzcyJdLCJncm91cCI6IlRlc3QiLCJhcHB0eXBlIjoiUHVibGljIiwiYXBwaWQiOiJnU2NoZWR1bGUudGVzdCIsImF1dGhtZXRob2QiOiJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6YWM6Y2xhc3NlczpQYXNzd29yZFByb3RlY3RlZFRyYW5zcG9ydCIsImF1dGhfdGltZSI6IjIwMTktMTEtMTVUMTE6NTI6MDUuMDU5WiIsInZlciI6IjEuMCJ9.CU5Ssl_3EvxL7s7Tcl2GpzSHoODD2hF81V7oQ-XjxPNdO-a-kZEoByOCsoBKLBOv6Yy8Zz-jrFD8j5yNC59YH6x8oz8udj8z7R1TFesNgptsVdGCgdhFWbtFStHgFf4XuTql5UQHhMUa-HZxFAcJNducPIZS5gyxhSxXXHWvJ6UXwDCAlTzzgzSlW0NG7z2Rh9pFB8A-yBsDZObuurnGK_EaOC_HgXjPXPY66hvvH6EL1YhIho7A5AKPi4ON9dQjQ3K3Ofa3GT1wQXUGHyiauiWaBIZ0U1sVlTXQsKWbwJi2uGMJaa0zr_UNGSNDXUL7CxXnRqqU8mtGPwRKuFLlYA";
    private final static String validAuth="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6InFhMDBtWlBMTVQtTFVjSG9KT0JIYklWZnJUbyJ9.eyJhdWQiOiJ1cm46Z1NjaGVkdWxlLnRlc3QiLCJpc3MiOiJodHRwOi8vQURGUzIudGhpc2lzZ2xvYmFsLmNvbS9hZGZzL3NlcnZpY2VzL3RydXN0IiwiaWF0IjoxNTc1Mjc4OTc3LCJleHAiOjE1NzUyODI1NzcsInVwbiI6IlN3YXBuYS5Ub290aWtvb3JhQGdsb2JhbC5jb20iLCJ1bmlxdWVfbmFtZSI6IlN3YXBuYSBUb290aWtvb3JhIiwiZW1haWwiOiJTd2FwbmEuVG9vdGlrb29yYUBnbG9iYWwuY29tIiwicm9sZSI6WyJTeXN0ZW1BZG1pbiIsIkFjY2VzcyJdLCJncm91cCI6IlRlc3QiLCJhcHB0eXBlIjoiUHVibGljIiwiYXBwaWQiOiJnU2NoZWR1bGUudGVzdCIsImF1dGhtZXRob2QiOiJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6YWM6Y2xhc3NlczpQYXNzd29yZFByb3RlY3RlZFRyYW5zcG9ydCIsImF1dGhfdGltZSI6IjIwMTktMTItMDJUMDk6Mjk6MjMuMzI2WiIsInZlciI6IjEuMCJ9.PbRGR7dvgu48sXm2TbqYnYB_PUHI6BfkcG5f_2ZPwy_nMcG3xligX78d9fKWNrp06c3X0CPzjoj50boTG0uJ2oeWKWdhVrAzTMY-tLwrgzonUaMvwWXPd6RUP_Sr0cB0mIJs2WFox-AyCCcUvJHkQwvtcz1dI6Mkeu5EhGBkD9dq1V3tWD73YtH7k8txUAMflDi-JV2uHYWMustfLnOir03jWt35Jj81Bx0Sg7PSpRdlRis1i9-sNiz8oJcta89uiQR5Eg1p88FYu9TyBm0PJgSbWuh8LYRyev4BdE0qpIWlVtRlrdzsRCecomkBY4-RDbDqGo9AWkLFEZ9ZuzZdBA";
    private final static String devAuthToken="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6InFhMDBtWlBMTVQtTFVjSG9KT0JIYklWZnJUbyJ9.eyJhdWQiOiJ1cm46Z1NjaGVkdWxlLmRldjEiLCJpc3MiOiJodHRwOi8vQURGUzIudGhpc2lzZ2xvYmFsLmNvbS9hZGZzL3NlcnZpY2VzL3RydXN0IiwiaWF0IjoxNTczODI5MjQ5LCJleHAiOjE1NzM4MzI4NDksInVwbiI6IlN3YXBuYS5Ub290aWtvb3JhQGdsb2JhbC5jb20iLCJ1bmlxdWVfbmFtZSI6IlN3YXBuYSBUb290aWtvb3JhIiwiZW1haWwiOiJTd2FwbmEuVG9vdGlrb29yYUBnbG9iYWwuY29tIiwicm9sZSI6WyJTeXN0ZW1BZG1pbiIsIkFjY2VzcyJdLCJncm91cCI6IkRldiIsImFwcHR5cGUiOiJQdWJsaWMiLCJhcHBpZCI6ImdTY2hlZHVsZS5kZXYxIiwiYXV0aG1ldGhvZCI6InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjIuMDphYzpjbGFzc2VzOlBhc3N3b3JkUHJvdGVjdGVkVHJhbnNwb3J0IiwiYXV0aF90aW1lIjoiMjAxOS0xMS0xNVQxMTo1MjowNS4wNTlaIiwidmVyIjoiMS4wIn0.XdAoMvBHZyv37bMbX0mK950ziXvRS07oppS_KiVRyTeU2RTZKMLXj4ff_LZ-uzM_7SHo6HW2x_WVR1qY9KWjx7l_sIyMSTryIGWzzlceQfXbENRAEB0GlbjdP4_jxuEV17ibDfT7enktU1E0XZ9-SMQcbGwYU9_xRZsLZOKXaPuXzQ3nDe7sOoblPu-_htS9Qu71tcMcPl_YmjjdOKvhlD9kA9wak47gwFe-p4QcyJgsDszHCM85_Cfb9O715-qjmPSJwC0-pOJOAY3t1cjMMaXVVQ2zV1oDt9PsQIqY2tepQFjwLum1ACoi3BVNrJoPLp0CvW-C-lRHnYvDRwLNNQ";
    /* setting up the env... details*/
    @BeforeClass
    public static void setUp() throws IOException {
        String env = Utilities.envName();
        envDetails.envDetails(env);
        System.out.println(env);
        url = envDetails.gScheduleUrl;
        System.out.println(url);
        //latestToken= Credentials_gSchedule.getBearerToken();
        //System.out.println(latestToken);
    }


    /* This test returns response code:401 when Bearer token is  null */
    @Test
    public void nullBearertoken() throws IOException {

        Response response = APIRequests.executeRequest(url, logGroupReportPath, "", "", "Get");
        System.out.println(">>>>> " + response.getStatusCode());
        Assert.assertEquals("This is a failure as the status codes don't match\n", 401, response.getStatusCode());
        System.out.println("The http status code is \n\n");
        response.getBody().prettyPrint();


    }

    /* This test returns response code:401 when Bearer token is invalid  */
    @Test
    public void inValidBearertoken() throws IOException {

        Response response = APIRequests.executeRequest(logGroupReportURL, logGroupReportPath, "", invalidAuthentication, "Get");
        System.out.println(">>>>> " + response.getStatusCode());
        Assert.assertEquals("This is a failure as the status codes don't match\n", 401, response.getStatusCode());
        String responseBody = response.asString();
        System.out.println("The LogReport can't be created :\n" + responseBody);
    }



    /* This test returns response code:200 ,generate the report  and report is sending to the given mail id ,when Bearer token is given */
    /*@Test
    public void getLogReportUsingPayload() throws IOException {
        String filename = "LogGroupRequestwithnoTransmitters.json";
        String filePath = Utilities.buildFilePath("LogReporter/", filename);
        String payload = Utilities.readFile(filePath);

        //Response response = APIRequests.executeRequest(url, path, payload, validAuthentication, "get");
        Response response =
                given()
                        .headers(
                                "Authorization",
                                validAuthentication
                                )
                       // .queryParam()

                        .relaxedHTTPSValidation()
                        .body(payload)
                        .when()
                        .get(url+fileSep+path)
                        .then()
                        //.contentType(ContentType.JSON)
                        .extract()
                        .response();
        System.out.println(">>>>> " + response.getStatusCode());

        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        System.out.println("The http status code is \n\n");
        response.getBody().prettyPrint();

    }*/


    /* This API request GET is for testing the  health check and returns LogReport State=Healthy */
    @Test
    public void getHealthCheck() throws IOException {

        Response response = APIRequests.executeRequest(logGroupReportURL, healthcheckPath, "", validAuth, "Get");
        System.out.println(">>>>> " + response.getStatusCode());
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        System.out.println("The http status code is \n\n");
        response.getBody().prettyPrint();
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("State of the LogReport : \n" + jsonPath.getString("state"));
    }

    /* This API request GET is for testing the  health check when Bearer token is invalid, Response Code:401 */
    @Test
    public void getHealthCheckWithInvalidBearerToken() throws IOException {

        Response response = APIRequests.executeRequest(healthcheckUrl, healthcheckPath, "", invalidAuthentication, "Get");
        System.out.println(">>>>> " + response.getStatusCode());
        Assert.assertEquals("This is a failure as the status codes don't match\n", 401, response.getStatusCode());
        System.out.println("The http status code is \n\n:" + response.getBody());


    }

    /* This test returns response code:200 ,generate the report  and report is sending to the given mail id ,when Bearer token is given */
    @Test
    public void getLogReport() throws IOException {
        // Response response = APIRequests.executeRequest(url, logGroupReportPath, "", validAuthentication,  "Get");
//Response response = APIRequests.executeRequestWithParams(url, testPath, "", validAuth, queryParamPath,"Get");
        String filePath = Utilities.buildFilePath("gScheduleOutput/", "LogReport.pdf");
        File file=new File(filePath);
        if(file.exists())
        {
            file.delete();
        }
        byte[] pdf = given()
                .contentType("application/pdf")
                .accept("*/*")
                .headers(
                        "Authorization",
                        validAuth
                )
                .relaxedHTTPSValidation()
                .when()
                .get(url + fileSep + logGroupReportPath)
                .then()
               .contentType(ContentType.JSON)
                .extract()
                .asByteArray();
       Utilities.downloadLocally(pdf,filePath);
        Assert.assertTrue(file.exists());
    }


    @Test
    public void getSingleLogReport() throws IOException {
        //Response response = APIRequests.executeRequestWithParams(logReportURL, logReportPath, "", validAuthentication, queryParam,"Get");
        Response response = given()

                .headers(
                        "Authorization",
                        validAuth
                )
                .relaxedHTTPSValidation()
                .when()
                .get(logGroupReportURL + fileSep + singleLogReportPath)
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .response();
        System.out.println(">>>>> " + response.getStatusCode());
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        System.out.println("The http status code is \n\n");
        response.getBody().prettyPrint();

    }


    @Test
    public void getLogGroupWithSingleLogReport() throws IOException {
        //Response response = APIRequests.executeRequestWithParams(logReportURL, logReportPath, "", validAuthentication, queryParam,"Get");
       /* Response response = APIRequests.executeRequest(url, LogGroupWithSingleLogReportPath, "", validAuth, "Get");
        System.out.println(">>>>> " + response.getStatusCode());
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        response.getBody().prettyPrint();*/



        Response response = given()

                .headers(
                        "Authorization",
                        validAuth
                )
                .relaxedHTTPSValidation()
                .when()
                .get(url + fileSep + LogGroupWithSingleLogReportPath)
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .response();
        System.out.println(">>>>> " + response.getStatusCode());
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        System.out.println("The http status code is \n\n");
        response.getBody().prettyPrint();

    }

    /*This test is to get all secure list of the logs using the request : GET*/
    @Test
    public void getAllSecureListOfLogs() {

        Response response = APIRequests.executeRequest(url, secureListOfLogsPath, "", validAuth, "Get");
        System.out.println(">>>>> " + response.getStatusCode());
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        response.getBody().prettyPrint();

    }

    /*This test is to returns a list of the logs for a log group : GET*/
    @Test
    public void getAllListOfLogsForaLogGroup() {

        Response response = APIRequests.executeRequest(url, listOfLogGroupLogsPath, "", validAuth, "Get");
        System.out.println(">>>>> " + response.getStatusCode());
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        response.getBody().prettyPrint();

    }

    /*This test is to returns a list of Groups that the current user is permitted to see*/
    @Test
    public void getAllCurrentUserListOfGroups() {

        Response response = APIRequests.executeRequest(url, currentUserLogGroupsPath, "", validAuth, "Get");
        System.out.println(">>>>> " + response.getStatusCode());
        Assert.assertEquals("This is a failure as the status codes don't match\n", 200, response.getStatusCode());
        response.getBody().prettyPrint();

    }
    /*This test is to returns 401 response code when remove all security groups for the given user by deleting them in the RAUSERSECURITYGROUPS table*/
    @Test
    public void logReportNotReturnedbyAPI() {

        Response response = APIRequests.executeRequest(url, currentUserLogGroupsPath, "", validAuth, "Get");
        System.out.println(">>>>> " + response.getStatusCode());
        Assert.assertEquals("no content", 401, response.getStatusCode());
        response.getBody().prettyPrint();

    }


}



/*LogGroupId:232- LogID:194
        LogGroupId:233-LogId:195
        LogGroupId303-LogIds:7,8,40*/
//Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6InFhMDBtWlBMTVQtTFVjSG9KT0JIYklWZnJUbyJ9.eyJhdWQiOiJ1cm46Z1NjaGVkdWxlLnRlc3QiLCJpc3MiOiJodHRwOi8vQURGUzIudGhpc2lzZ2xvYmFsLmNvbS9hZGZzL3NlcnZpY2VzL3RydXN0IiwiaWF0IjoxNTcyMzU2OTc3LCJleHAiOjE1NzIzNjA1NzcsInVwbiI6IlN3YXBuYS5Ub290aWtvb3JhQGdsb2JhbC5jb20iLCJ1bmlxdWVfbmFtZSI6IlN3YXBuYSBUb290aWtvb3JhIiwiZW1haWwiOiJTd2FwbmEuVG9vdGlrb29yYUBnbG9iYWwuY29tIiwicm9sZSI6WyJTeXN0ZW1BZG1pbiIsIkFjY2VzcyJdLCJncm91cCI6IlRlc3QiLCJhcHB0eXBlIjoiUHVibGljIiwiYXBwaWQiOiJnU2NoZWR1bGUudGVzdCIsImF1dGhtZXRob2QiOiJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6YWM6Y2xhc3NlczpQYXNzd29yZFByb3RlY3RlZFRyYW5zcG9ydCIsImF1dGhfdGltZSI6IjIwMTktMTAtMjlUMTM6NDk6MTUuMzY5WiIsInZlciI6IjEuMCJ9.okXLsQ-zyQXXy5LS5pOfdz_PaSQLG_B6XqQaK_Xti2DuE2iW_1OZl8TPnaXFfMd6_VG3lHf_TdXy6Z9bGW3VvFPT-zEXJmy1LFrpWNkso-MaBgrTVZCz7sa8acuJeXfqdhjSZDfXjVSgWd2ghEZJ5HjNwMppb2aNoClvCN45smHt5JNoRTBbgNvLFCdYw_h8GhDZQH2gfb-opvMn4oqFlOuqZDKLmoQ0jQtoha7KDEWPpfJZ4nJ8pY2mBfxuQfAkS0E3JZbl-Vo61l68FTczG9D3uatEBRyTrwAbpBDeIgE1C_XPDKsuX7pvzpilL-f1BXIpVukzZmyzUrht3OZ-kw
//https://gscheduletest.thisisglobal.com/api/Core/LogManagement/Log/Fetch/195(to get unique id of the log)

        /*https://gscheduletest.thisisglobal.com/api/Core/LogManagement/Log/ListSecure

        https://gscheduletest.thisisglobal.com/api/Core/LogManagement/Log/ListSecure/607


        https://gscheduletest.thisisglobal.com/api/Core/LogManagement/LogGroup/Administration/ListSecure*/

        //CCP-logGroupId=412(transmitter idorlogids...155,156)
