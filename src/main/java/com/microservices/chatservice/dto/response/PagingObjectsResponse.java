package com.microservices.chatservice.dto.response;

import java.util.List;

public record PagingObjectsResponse<T>(
        Integer totalPages,
        Long totalElements,
        Integer number,
        Integer size,
        Integer numberOfElements,
        Boolean first,
        Boolean last,
        List<T> content
) {
}
