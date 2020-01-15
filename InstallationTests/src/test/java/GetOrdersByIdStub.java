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

public class GetOrdersByIdStub {
    public static String jsonresponse = "[\n" +
            "{\n" +
            "\"id\": \"ec295452-c32d-44ee-b79f-770c4254b31e\",\n" +
            "\"name\": \"Order Name 1\",\n" +
            "\"orderNumber\": 1,\n" +
            "\"clientId\": \"0b1d0d73-264e-4907-b59c-ac8fa7ec9e4f\",\n" +
            "\"contactIds\": [\n" +
            "\"515ae658-1b09-4387-8122-0f3b763aa986\",\n" +
            "\"88b3a64e-f6ae-4775-8f1e-b9132f0fa71a\"\n" +
            "],\n" +
            "\"ownerIds\": [\n" +
            "\"8d42c385-d894-4d81-98a1-d35ae317446f\",\n" +
            "\"abad4fef-7471-403b-857c-8c7a93f513e4\"\n" +
            "],\n" +
            "\"status\": 1\n" +
            "}\n" +
            "]";

    @ClassRule
public static HoverflyRule hoverflyRule = HoverflyRule
        .inSimulationMode(dsl(service("http://10.2.90:8012").get(startsWith("/api/Orders/\"e7b65789-13a7-48ee-865f-7e7d9c4e89c8\""))
                .willReturn(success("jsonResponse", "application/json"))))
                .printSimulationData();

    @Test
public void GetOrdersByIdStubTest() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> ownersResponse = restTemplate.getForEntity("http://10.2.90:8012/api/Orders/\"e7b65789-13a7-48ee-865f-7e7d9c4e89c8\"", String.class);
    System.out.println("Response code : \n" + ownersResponse.getStatusCode());
    System.out.println("Response body : \n\n " + ownersResponse.getBody());
    System.out.println(ownersResponse.getStatusCodeValue());
    Assert.assertEquals("Failure, status code didn't match", ownersResponse.getStatusCodeValue(), 200);
    }
}

