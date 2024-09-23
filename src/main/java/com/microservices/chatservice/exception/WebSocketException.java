package com.microservices.chatservice.exception;

public class WebSocketException extends RuntimeException {

    public WebSocketException(String message) {
        super(message);
    }

}
