package com.peerLender.lendingengine;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;


@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class LoanIntegrationTest {

    private static final String HU = "HU";
    private static  final Gson GSON = new Gson();

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int serverPort;

    @Test
    public void givenLoanRequestMadeLoanApplicationsGetsCreated() throws Exception {
        final String baseUrl = "http://localhost" + serverPort + "/loan/";
        HttpHeaders httpHeaders = new HttpHeaders();




    }

}
