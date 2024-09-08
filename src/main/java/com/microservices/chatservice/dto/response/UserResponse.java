package com.microservices.chatservice.dto.response;

import com.microservices.chatservice.constant.UserStatus;

import java.io.Serializable;

/**
 * DTO for {@link com.microservices.chatservice.entity.User}
 */
public record UserResponse(
        String id,
        UserStatus status
) implements Serializable {
}
