package com.adidas.common.publicservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class JacksonConfiguration {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public JavaTimeModule dateTimeModule(){
        return new JavaTimeModule();
    }
}