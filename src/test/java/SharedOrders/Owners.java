package SharedOrders;

import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.startsWith;
import static io.specto.hoverfly.junit.rule.HoverflyRule.inSimulationMode;

/* This is a stub test using hoverfly*/
public class Owners {

    public static String jsonResponse = "[\n" +
            "  {\n" +
            "    \"id\": \"9245fe4a-d402-451c-b9ed-9c1a04247482\",\n" +
            "    \"firstName\": \"Owner 1 First Name\",\n" +
            "    \"lastName\": \"Owner 1 Last Name\",\n" +
            "    \"isDisabled\": false,\n" +
            "    \"capabilities\": [\n" +
            "      1\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"6245fe4a-d402-451c-b9ed-9c1a04247482\",\n" +
            "    \"firstName\": \"Owner 2 First Name\",\n" +
            "    \"lastName\": \"Owner 2 Last Name\",\n" +
            "    \"isDisabled\": true,\n" +
            "    \"capabilities\": [\n" +
            "      1\n" +
            "    ]";


    @ClassRule
    public static HoverflyRule hoverflyRule = inSimulationMode(dsl(service("http://10.12.114.80:9080").get(startsWith("/api/People/Capability=Owner"))
            .willReturn(success(jsonResponse, "application/json"))))
            .printSimulationData();


  /* When actual URI is available the made up URI can be replaced with the actual and the ClassRule above can be commented out*/
    @Test

    public void getOwnersTest() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> ownersResponse = restTemplate.getForEntity("http://10.12.114.80:9080/api/People/Capability=Owner", String.class);
        System.out.println("Response code : \n" + ownersResponse.getStatusCode());
        System.out.println("Response body : \n\n " + ownersResponse.getBody());
        System.out.println(ownersResponse.getStatusCodeValue());
        Assert.assertEquals("Failure, status code didn't match", ownersResponse.getStatusCodeValue(), 200);
    }
}





