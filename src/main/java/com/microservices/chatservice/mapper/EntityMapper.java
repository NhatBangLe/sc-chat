package com.microservices.chatservice.mapper;

public interface EntityMapper<T, R> {

    R toResponse(T entity);

}