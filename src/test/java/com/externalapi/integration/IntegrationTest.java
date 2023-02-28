package com.externalapi.integration;

import com.externalapi.entity.Television;
import org.assertj.core.api.WithAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest implements WithAssertions {
    private static final String CONTENT_TYPE_JSON = "application/json";
    private URL baseUrl;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.baseUrl = new URL("http://localhost:8080"+"/");
    }
    @Test
    void getAll() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", CONTENT_TYPE_JSON);

        ResponseEntity<Television> response = template.getForEntity(baseUrl.toString() + "televisions", Television.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getTelevisionById() throws Exception{


        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", CONTENT_TYPE_JSON);

        ResponseEntity<Television> response = template.getForEntity(baseUrl.toString() + "televisions?id=5", Television.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}
