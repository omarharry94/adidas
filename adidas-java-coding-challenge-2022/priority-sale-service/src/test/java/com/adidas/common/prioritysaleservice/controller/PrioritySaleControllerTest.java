package com.adidas.common.prioritysaleservice.controller;

import com.adidas.common.dto.AdiClubMemberInfoDto;
import com.adidas.common.prioritysaleservice.service.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PrioritySaleControllerTest {
    EmailService emailService = Mockito.mock(EmailService.class);
    PrioritySaleController prioritySaleController = new PrioritySaleController(emailService);

    @Test
    void addUserToQueue() {
        Mockito.doNothing().when(emailService).putEmailJob(Mockito.any(AdiClubMemberInfoDto.class));
        ResponseEntity<String> stringResponseEntity = prioritySaleController.addUserToQueue(AdiClubMemberInfoDto.builder()
                .email("email@test.com")
                .points(123)
                .registrationDate(Instant.now())
                .build());
        Assertions.assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
    }
}