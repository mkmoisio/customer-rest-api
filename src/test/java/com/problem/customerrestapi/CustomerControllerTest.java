package com.problem.customerrestapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;


import java.util.Collections;
import java.util.HashMap;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private static final HashMap<String, JSONObject> dummyCustomers = new HashMap<>();
    private static final HashMap<String, JSONObject> realCustomers = new HashMap<>();

    private static final HttpHeaders headers = new HttpHeaders();
    private String buildUrl() {
        return "http://localhost:" + this.port + "/customers";
    }
    @BeforeAll()
    static void init() throws JSONException {

        dummyCustomers.put("nonamer with email", new JSONObject().put("email", "something@example.com"));
        dummyCustomers.put("name only", new JSONObject().put("name", "Asiakas 1"));
        dummyCustomers.put("name and email", new JSONObject().put("name", "Asiakas 1").put("email", "something@example.com"));
        dummyCustomers.put("name and bad email", new JSONObject().put("name", "Asiakas 1").put("email", "something@exam)€ple.com"));
        dummyCustomers.put("name and businessId", new JSONObject().put("name", "Asiakas 1").put("businessId", "1234567-0"));
        dummyCustomers.put("name and bad businessId", new JSONObject().put("name", "Asiakas 1").put("businessId", "123456722-0"));
        dummyCustomers.put("name and bad businessId with incorrect check sum", new JSONObject().put("name", "Asiakas 1").put("businessId", "1234567-1"));
        dummyCustomers.put("alma", new JSONObject().put("name", "Alma").put("businessId", "1944757-4"));

        realCustomers.put("Alma", new JSONObject().put("name", "Alma").put("businessId", "1944757-4"));
        realCustomers.put("Atria A", new JSONObject().put("name", "Atria A").put("businessId", "0841066-1"));
        realCustomers.put("Fiskars", new JSONObject().put("name", "Fiskars").put("businessId", "0214036-5"));


        headers.put("Content-Type", Collections.singletonList(APPLICATION_JSON_VALUE));
    }

    @Test
    void nonExistingPathShouldReturn404() throws Exception {
        assertThat(this.restTemplate.getForObject(buildUrl() + "garbage",
                String.class)).contains("404");
    }

    @Test
    void getCustomersShouldReturnResponse() {

        ResponseEntity<String> response = restTemplate.
                getForEntity(buildUrl(), String.class);
        System.out.println(response);
        HttpStatusCode code = response.getStatusCode();
        assertEquals("Status code mismatch", HttpStatus.OK.value(), code.value());
    }

    @Test
    void getCustomer_NonExisting_ShouldReturn404() {
        ResponseEntity<String> response = restTemplate.
                getForEntity(buildUrl()+"/1000", String.class);
        System.out.println(response);
        assertEquals("Status code mismatch", HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());

    }

    @Test
    void postCustomers_NameOnly_ShouldReturn201() {
        HttpEntity<String> request = new HttpEntity<>(dummyCustomers.get("name only").toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(buildUrl(), request, String.class);
        assertTrue("no id", response.getBody().contains("id"));
        assertTrue("incorrect name name", response.getBody().contains("\"name\":\"Asiakas 1\""));
        assertEquals("Status code mismatch", HttpStatus.CREATED.value(), response.getStatusCode().value());
        System.out.println(response);
    }

    @Test
    void postCustomers_NameMissing_ShouldReturn400() {
        HttpEntity<String> request = new HttpEntity<>(dummyCustomers.get("nonamer with email").toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(buildUrl(), request, String.class);
        assertEquals("Status code mismatch", HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
        System.out.println(response);

    }
    @Test
    void postCustomers_InvalidBusinessId_ShouldReturn400() {
        HttpEntity<String> request = new HttpEntity<>(dummyCustomers.get("name and bad businessId with incorrect check sum").toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(buildUrl(), request, String.class);
        assertEquals("Status code mismatch", HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
        System.out.println(response);

    }

    @Test
    void postCustomers_InvalidEmail_ShouldReturn400() {
        HttpEntity<String> request = new HttpEntity<>(dummyCustomers.get("name and bad email").toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(buildUrl(), request, String.class);
        assertEquals("Status code mismatch", HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
        System.out.println(response);

    }

    @Test
    void postCustomers_ValidWithBusinessId() {
        realCustomers.entrySet().stream().map(item ->new HttpEntity<>(item.getValue().toString(), headers)).forEach(request -> {
            ResponseEntity<String> response = restTemplate.postForEntity(buildUrl(), request, String.class);
            System.out.println(response);
        });


        ResponseEntity<String> getResponse = restTemplate.
                getForEntity(buildUrl(), String.class);
        System.out.println(getResponse);

    }
}
