package com.adidas.common.publicservice.controller;

import com.adidas.common.dto.AdiClubMemberInfoDto;
import com.adidas.common.publicservice.config.AdiClubConfigProperties;
import com.adidas.common.publicservice.config.PriorityQueueProperties;
import com.adidas.common.utils.RestService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


class SubscriptionRestControllerTest {

    private  final AdiClubConfigProperties adiClubConfigProperties = new AdiClubConfigProperties();
    private  final PriorityQueueProperties priorityQueueProperties = new PriorityQueueProperties();

    private SubscriptionRestController subscriptionRestController;

    @BeforeEach()
     void before() {
        adiClubConfigProperties.setUrl("http://localhost:9090");
        adiClubConfigProperties.setPath("/getSomething");
        adiClubConfigProperties.setMethod(HttpMethod.GET);
        adiClubConfigProperties.setHeaders(HttpHeaders.EMPTY);

        priorityQueueProperties.setUrl("http://localhost:9090");
        priorityQueueProperties.setPath("/getSomethingDiffent");
        priorityQueueProperties.setMethod(HttpMethod.POST);
        priorityQueueProperties.setHeaders(HttpHeaders.EMPTY);

        subscriptionRestController = new SubscriptionRestController(adiClubConfigProperties, priorityQueueProperties);
    }


    @Test
    void addSubscriberToQueue() {

        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.add("emailAddress", "test@gmail.com");

        try (MockedStatic<RestService> utilities = Mockito.mockStatic(RestService.class)) {


            AdiClubMemberInfoDto email = AdiClubMemberInfoDto.builder().email("email").build();
            utilities.when(() -> RestService.buildUrlAndSendRequest(Mockito.eq(adiClubConfigProperties),
                    Mockito.eq(queryParameters),
                    Mockito.any())).thenReturn(email);
            utilities.when(() -> RestService.buildUrlAndSendRequest(Mockito.eq(priorityQueueProperties),
                    Mockito.eq(null),
                    Mockito.eq(email))).thenReturn(StringUtils.EMPTY);

            ResponseEntity<String> response = subscriptionRestController.addSubscriberToQueue("test@gmail.com");

            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        }

    }

    @Test
    void addSubscriberToQueueNotFound() {

        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.add("emailAddress", "test@gmail.com");

        try (MockedStatic<RestService> utilities = Mockito.mockStatic(RestService.class)) {
            utilities.when(() -> RestService.buildUrlAndSendRequest(Mockito.eq(adiClubConfigProperties),
                    Mockito.eq(queryParameters),
                    Mockito.any())).thenReturn(null);

            ResponseEntity<String> response = subscriptionRestController.addSubscriberToQueue("test@gmail.com");

            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        }

    }

}