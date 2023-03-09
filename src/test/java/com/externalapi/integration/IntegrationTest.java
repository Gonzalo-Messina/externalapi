package com.externalapi.integration;

import com.externalapi.entity.Television;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import java.util.List;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest implements WithAssertions {
    private static final String CONTENT_TYPE_JSON = "application/json";
    private URL baseUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.baseUrl = new URL("http://localhost:"+ this.port);
    }
    @Test
    void getAll(){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", CONTENT_TYPE_JSON);

        ResponseEntity<List> response = template.getForEntity(baseUrl + "/televisions", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void getTelevisionById() throws Exception{


        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", CONTENT_TYPE_JSON);

        ResponseEntity<Television> response = template.getForEntity(baseUrl + "/televisions/id?id=5", Television.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}
