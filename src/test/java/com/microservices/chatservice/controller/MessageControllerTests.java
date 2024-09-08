package com.microservices.chatservice.controller;

import com.microservices.chatservice.ChatServiceApplicationTests;
import com.microservices.chatservice.constant.MessageType;
import com.microservices.chatservice.constant.UserStatus;
import com.microservices.chatservice.entity.Message;
import com.microservices.chatservice.entity.User;
import com.microservices.chatservice.repository.ConversationRepository;
import com.microservices.chatservice.repository.MessageRepository;
import com.microservices.chatservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;

class MessageControllerTests extends ChatServiceApplicationTests {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    private Long conversationId;

    @BeforeEach
    void initConversation() {
        final String creatorId = "fad9b421-6d0e-42ec-a30b-450e8b4e3dc2";
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
        this.conversationId = Long.parseLong(conversationId);
    }

    @Test
    void getAllMessagesWithUnavailableConversationId_shouldReturnOK() {
        given(requestSpecification)
                .pathParam("conversationId", 3453543L)
                .when()
                .get("/{conversationId}")
                .then()
                .statusCode(200);
    }

    @Test
    void getAllMessagesWithAvailableConversationId_shouldReturnOK() {
        given(requestSpecification)
                .pathParam("conversationId", conversationId)
                .when()
                .get("/{conversationId}")
                .then()
                .statusCode(200);
    }

    @Test
    void deleteMessageWithUnavailableMessageId_shouldReturnNoContent() {
        given(requestSpecification)
                .pathParam("messageId", 34563456L)
                .when()
                .delete("/{messageId}")
                .then()
                .statusCode(204);
    }

    @Test
    void deleteMessageWithAvailableMessageId_shouldReturnNoContent() {
        var sender = userRepository.save(User.builder()
                .id("fad9b421-6d0e-42ec-a30b-450e8b5e3dc2")
                .status(UserStatus.ONLINE)
                .build()
        );
        var conversation = conversationRepository.findById(conversationId).orElseThrow(AssertionError::new);
        var messageId = messageRepository
                .save(Message.builder()
                        .text("Message")
                        .type(MessageType.TEXT)
                        .conversation(conversation)
                        .sender(sender)
                        .build()
                ).getId();
        given(requestSpecification)
                .pathParam("messageId", messageId)
                .when()
                .delete("/{messageId}")
                .then()
                .statusCode(204);
    }

}