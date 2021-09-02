package org.amuji.ninjagateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "httpbin=http://localhost:${wiremock.server.port}",
                "token-service.url=http://localhost:${wiremock.server.port}",
                "token-service.username=userx",
                "token-service.password=123456"})
@AutoConfigureWireMock(port = 0)
class ApplicationTests {

    @Autowired
    private WebTestClient client;

    @Test
    void given_proxy_is_up_and_running_when_access_target_then_token_should_be_added_automatically() {
        //token service
        stubFor(post(urlEqualTo("/tokens"))
                .withRequestBody(equalToJson("{\"username\": \"userx\", \"password\": \"123456\"}", true, false))
                .willReturn(aResponse()
                        .withBody("THE_FAKE_TOKEN")
                        .withHeader("ContentType", MediaType.TEXT_PLAIN_VALUE)));

        //target service
        stubFor(get(urlEqualTo("/get"))
                .withHeader("hello", equalTo("world"))
                .withHeader("token", equalTo("THE_FAKE_TOKEN"))
                .willReturn(aResponse()
                        .withBody("{\"product\": {\"name\": \"watermelon\"}}")
                        .withHeader("ContentType", "application/json")));


        client.get().uri("/get")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.name", equalTo("watermelon"));

    }
}
