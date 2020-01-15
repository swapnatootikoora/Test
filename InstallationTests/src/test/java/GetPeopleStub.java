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

public class GetPeopleStub {
    public static String jsonresponse = "[\n" +
            "{ \"id\": \"0f02976b-a0c5-45a8-bacc-aa9500a6c838\"," +
            "\"firstName\": \"Amy\"," +
            "\"lastName\": \"Davies\"," +
            "\"isDisabled\": false }\n" +
            "\n" +
            ",\n" +
            "\n" +

            "{ \"id\": \"7a2afebb-b1c8-4110-9dfc-aa9500a6c838\"," +
            "\"firstName\": \"Jan\"," +
            "\"lastName\": \"Mccormick\"," +
            "\"isDisabled\": false }\n" +
            "\"Capabilities\": [\n" +
            "\"Owner\"\n" +
            "]\n" +
            "\n" +
            ",\n" +
            "\n" +

            "{ \"id\": \"346131ac-2c74-4938-aa3a-aa9500a6c839\"," +
            "\"firstName\": \"Louise\"," +
            "\"lastName\": \"Kearns\"," +
            "\"isDisabled\": false }\n" +
            "\"Capabilities\": [\n" +
            "\"Owner\"\n" +
            "\"Planner\"\n" +
            "]\n" +
            "\n" +
            "]";
@ClassRule
public static HoverflyRule hoverflyRule = HoverflyRule
        .inSimulationMode(dsl(service("http://10.2.90:8012").get(startsWith("/api/People/Owners"))
                .willReturn(success("jsonResponse", "application/json"))))
        .printSimulationData();
@Test
public void GetPeopleStubTest() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> ownersResponse = restTemplate.getForEntity("http://10.2.90:8012/api/People/Owners", String.class);
    System.out.println("Response code : \n" + ownersResponse.getStatusCode());
    System.out.println("Response body : \n\n " + ownersResponse.getBody());
    System.out.println(ownersResponse.getStatusCodeValue());
    Assert.assertEquals("Failure, status code didn't match", ownersResponse.getStatusCodeValue(), 200);
    }
}

