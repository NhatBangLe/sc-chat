package com.microservices.chatservice;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@NoArgsConstructor
public class ChatServiceApplicationTests {

    final static String API_VERSION = "v1";

    @LocalServerPort
    protected int port;

    protected RequestSpecification requestSpecification;

    @BeforeEach
    public void setUp() {
        requestSpecification = new RequestSpecBuilder()
                .setPort(port)
                .setBasePath("/api/" + API_VERSION + "/chat")
                .addHeader("Content-Type", "application/json")
                .build();
    }

}
