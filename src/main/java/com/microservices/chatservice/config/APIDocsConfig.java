package com.microservices.chatservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIDocsConfig {

    @Bean
    public OpenAPI getOpenAPI() {
        var info = new Info()
                .title("Chat Service")
                .version("1.0.0")
                .description(
                        """
                                NOTE: ALL DATE-TIME VALUES ARE LONG DATATYPE (INTEGER 64-BIT).
                                
                                This service includes:\

                                1. WebSocket - STOMP provides real-time chatting ability (not showing here).\

                                2. All HTTP endpoints support to query data."""
                );
        var openApi = new OpenAPI();
        openApi.setInfo(info);

        return openApi;
    }

}
