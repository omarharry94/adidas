package com.adidas.common.emailservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class DummyEmailRestControllerTest {
    private final DummyEmailRestController dummyEmailRestController = new DummyEmailRestController();

    @Test
    void getDummyEndpointResponse() {
        ResponseEntity<String> dummyEndpointResponse = dummyEmailRestController.getDummyEndpointResponse("test@test.com");
        Assertions.assertEquals(HttpStatus.OK, dummyEndpointResponse.getStatusCode());
    }
}