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

public class GetClientsContactsStub {
    public static String jsonresponse = "[\n" +
            "{\n" +
            "\"id\": \"b4f90d15-0107-411e-99bf-3b4cbed76251\",\n" +
            "\"firstName\": \"Bob\",\n" +
            "\"lastName\": \"Smith\",\n" +
            "\"isDisabled\": false,\n" +
            "\"clientId\": \"74dc7b06-2e2c-4112-867b-aa840105b97b\"\n" +
            "}\n" +
            "]";
    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule
            .inSimulationMode(dsl(service("http://10.2.90:8012").get(startsWith("/api/Clients/b4f90d15-0107-411e-99bf-3b4cbed76251/contacts"))
                    .willReturn(success("jsonResponse", "application/json"))))
            .printSimulationData();
    @Test
    public void GetClientsContactsStubTest() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> ownersResponse = restTemplate.getForEntity("http://10.2.90:8012/api/Clients/b4f90d15-0107-411e-99bf-3b4cbed76251/contacts", String.class);
        System.out.println("Response code : \n" + ownersResponse.getStatusCode());
        System.out.println("Response body : \n\n " + ownersResponse.getBody());
        System.out.println(ownersResponse.getStatusCodeValue());
        Assert.assertEquals("Failure, status code didn't match", ownersResponse.getStatusCodeValue(), 200);
    }
}
