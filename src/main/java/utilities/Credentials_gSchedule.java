package utilities;

import apirequests.APIRequests;
import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Credentials_gSchedule  {

    //this is for authentication settings
    public static final String clientID = "gSchedule.test";
    public static final String returnURL = "https://gscheduletest.thisisglobal.com/api/Management/Security/Authentication/GetAuthenticationToken";
    public static final String resource = "urn:gSchedule.test";
    public static final String adfsSingleSignOnAuthURL = "https://adfs2.thisisglobal.com/adfs/oauth2/authorize";
    public static final String adfsSingleSignOnGetTokenURL = "https://adfs2.thisisglobal.com/adfs/oauth2/token";
    public static final String userName = "SystemAdmin.Test@thisisglobal.com";
    public static final String password = "D0nkeyK0ng";
    public static String bearerToken = "";



    public static String logIn(String clientID, String returnURL, String resource, String adfsSingleSignOnAuthURL, String adfsSingleSignOnGetTokenURL, String userName, String password) {
        Response response = APIRequests.executeRequest(adfsSingleSignOnAuthURL,
                "response_type=code&client_id=" + clientID + "&resource=" + resource + "&redirect_uri=" + returnURL,
                "UserName=" + userName + "; Password=" + password + "; AuthMethod='FormsAuthentication'",
                "",
                "post");
        String headers = response.getHeader("location");
        System.out.println("the headers are: " + response);
        System.out.println(response.asString());


        return bearerToken;
    }

    public static String getBearerToken()  throws IOException  {
        String path=System.getProperty("user.dir");
        String filePath = path + "/testdata/"+"GetAuthTokenOauth2.ps1";
        /*String cmd = "pwsh " + filePath;
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(cmd);
        process.getOutputStream().close();*/
        PowerShellResponse response;
        String token = "";
        try (PowerShell powerShell = PowerShell.openSession()) {
            //Increase timeout to give enough time to the script to finish
            Map<String, String> config = new HashMap<String, String>();
            config.put("maxWait", "80000");

            //Execute script
            response = powerShell.configuration(config).executeScript(filePath);
            //Print results if the script
            token = response.getCommandOutput();
            System.out.println("Script output:" + response.getCommandOutput());
        } catch (PowerShellNotAvailableException ex) {
            ex.printStackTrace();
        }
        return token;

    }
}