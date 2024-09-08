package com.microservices.chatservice.controller;

import com.microservices.chatservice.ChatServiceApplicationTests;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class ConversationControllerTests extends ChatServiceApplicationTests {

    private final String creatorId = "fad9b421-6d0e-42ec-a30b-450e8b4e3dc2";
    private final String userId = "f1035f0e-36ce-401f-b687-97b53e8ee52c";

    @Test
    void getAllConversationsWithUnavailableCreatorId_shouldReturnNotFound() {
        given(requestSpecification)
                .pathParam("creatorId", "not-found")
                .when()
                .get("/conversation/{creatorId}/user")
                .then()
                .statusCode(404);
    }

    @Test
    void getAllConversationsWithCreatorId_shouldReturnOK() {
        for (int i = 1; i <= 5; i++) {
            given(requestSpecification)
                    .body("""
                            {
                                "name": "Test conversation",
                                "type": "SINGLE",
                                "creatorId": "%s"
                            }""".formatted(creatorId))
                    .when()
                    .post("/conversation")
                    .thenReturn()
                    .print();
        }
        given(requestSpecification)
                .pathParam("userId", creatorId)
                .when()
                .get("/conversation/{userId}/user")
                .then()
                .statusCode(200);
    }

    @Test
    void getConversationWithUnavailableConversationId_shouldReturnNotFound() {
        given(requestSpecification)
                .pathParam("conversationId", 3453452342L)
                .when()
                .get("/conversation/{conversationId}")
                .then()
                .statusCode(404);
    }

    @Test
    void getConversationWithAvailableConversationId_shouldReturnOK() {
        var conversationId = given(requestSpecification)
                .body("""
                        {
                            "title": "Test conversation",
                            "type": "GROUP",
                            "creatorId": "%s"
                        }""".formatted(creatorId))
                .when()
                .post("/conversation")
                .thenReturn()
                .print();
        given(requestSpecification)
                .pathParam("conversationId", conversationId)
                .when()
                .get("/conversation/{conversationId}")
                .then()
                .statusCode(200);
    }

    @Test
    void createConversationWithBlankCreatorId_shouldReturnBadRequest() {
        given(requestSpecification)
                .body("""
                        {
                            "name": "Test conversation",
                            "creatorId": "%s"
                        }""".formatted(userId))
                .when()
                .post("/conversation")
                .then()
                .statusCode(400);
    }

    @Test
    void createConversationWithBlankTitle_shouldReturnBadRequest() {
        given(requestSpecification)
                .body("""
                        {
                            "name": "     ",
                            "creatorId": "%s"
                        }""".formatted(creatorId))
                .when()
                .post("/conversation")
                .then()
                .statusCode(400);
    }

    @Test
    void createConversation_shouldReturnCreated() {
        given(requestSpecification)
                .body("""
                        {
                            "title": "Test conversation",
                            "type": "SINGLE",
                            "creatorId": "%s"
                        }""".formatted(creatorId))
                .when()
                .post("/conversation")
                .then()
                .statusCode(201);
    }

    @Test
    void updateConversationWithBlankName_shouldReturnBadRequest() {
        var conversationId = given(requestSpecification)
                .body("""
                        {
                            "name": "Test conversation",
                            "firstUserId": "%s",
                            "secondUserId": "%s"
                        }""".formatted(creatorId, userId))
                .when()
                .post("/conversation")
                .thenReturn()
                .print();
        given(requestSpecification)
                .pathParam("conversationId", conversationId)
                .body("""
                        {
                            "name": "        "
                        }""")
                .when()
                .patch("/conversation/{conversationId}")
                .then()
                .statusCode(400);
    }

    @Test
    void updateConversationWithUnavailableConversationId_shouldReturnNotFound() {
        given(requestSpecification)
                .pathParam("conversationId", 353453L)
                .body("""
                        {
                            "title": "New conversation"
                        }""")
                .when()
                .patch("/conversation/{conversationId}")
                .then()
                .statusCode(404);
    }

    @Test
    void updateConversation_shouldReturnNoContent() {
        var conversationId = given(requestSpecification)
                .body("""
                        {
                            "title": "Test conversation",
                            "type": "SINGLE",
                            "creatorId": "%s"
                        }""".formatted(creatorId))
                .when()
                .post("/conversation")
                .thenReturn()
                .print();
        given(requestSpecification)
                .pathParam("conversationId", conversationId)
                .body("""
                        {
                            "name": "New conversation"
                        }""")
                .when()
                .patch("/conversation/{conversationId}")
                .then()
                .statusCode(204);
    }

    @Test
    void deleteConversationWithUnavailableConversationId_shouldReturnNoContent() {
        given(requestSpecification)
                .pathParam("conversationId", 3234234L)
                .when()
                .delete("/conversation/{conversationId}")
                .then()
                .statusCode(204);
    }

    @Test
    void deleteConversation_shouldReturnNoContent() {
        var conversationId = given(requestSpecification)
                .body("""
                        {
                            "title": "Test conversation",
                            "type": "SINGLE",
                            "creatorId": "%s"
                        }""".formatted(creatorId))
                .when()
                .post("/conversation")
                .thenReturn()
                .print();
        given(requestSpecification)
                .pathParam("conversationId", conversationId)
                .when()
                .delete("/conversation/{conversationId}")
                .then()
                .statusCode(204);
    }

}