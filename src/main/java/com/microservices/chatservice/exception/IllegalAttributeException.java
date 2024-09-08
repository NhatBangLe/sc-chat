package com.microservices.chatservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IllegalAttributeException extends IllegalArgumentException {
    public IllegalAttributeException(String message) {
        super(message);
    }
}
