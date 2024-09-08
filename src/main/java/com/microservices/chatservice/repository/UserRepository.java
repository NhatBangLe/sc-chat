package com.microservices.chatservice.repository;

import com.microservices.chatservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}