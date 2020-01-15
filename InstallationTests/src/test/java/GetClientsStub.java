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

public class GetClientsStub {
    public static String jsonresponse = "[\n" +
            "  {\n" +
            "    \"id\": \"b40b95ad-1f33-462f-b979-aa840105b97b\",\n" +
            "    \"name\": \"Ford cars\",\n" +
            "    \"customerReferenceNumber\": \"C1291896\",\n" +
            "    \"externalId\": null,\n" +
            "    \"isDisabled\": false,\n" +
            "    \"tradingType\": 1\n" +
            "  },\n" +
            "]";
    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule
            .inSimulationMode(dsl(service("http://10.2.90:8012").get(startsWith("/api/Clients"))
                    .willReturn(success("jsonResponse", "application/json"))))
            .printSimulationData();
    @Test
    public void getClientsStubTest() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> ownersResponse = restTemplate.getForEntity("http://10.2.90:8012/api/Clients", String.class);
        System.out.println("Response code : \n" + ownersResponse.getStatusCode());
        System.out.println("Response body : \n\n " + ownersResponse.getBody());
        System.out.println(ownersResponse.getStatusCodeValue());
        Assert.assertEquals("Failure, status code didn't match", ownersResponse.getStatusCodeValue(), 200);
    }
}
