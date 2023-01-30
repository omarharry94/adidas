package com.adidas.common.publicservice.controller;

import com.adidas.common.config.EndpointConfigProperties;
import com.adidas.common.dto.AdiClubMemberInfoDto;
import com.adidas.common.publicservice.config.AdiClubConfigProperties;
import com.adidas.common.publicservice.config.PriorityQueueProperties;
import com.adidas.common.service.RestService;
import com.adidas.common.service.impl.RestServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import reactor.core.publisher.Mono;


class SubscriptionRestControllerTest {

    private  AdiClubConfigProperties adiClubConfigProperties = Mockito.mock(AdiClubConfigProperties.class);
    private  PriorityQueueProperties priorityQueueProperties = Mockito.mock(PriorityQueueProperties.class);

    private SubscriptionRestController subscriptionRestController;

    @SuppressWarnings("unchecked")
    @BeforeEach()
     void before() {
        subscriptionRestController = new SubscriptionRestController(adiClubConfigProperties, priorityQueueProperties);
        RestService mockedRestService = Mockito.mock(RestService.class);
        subscriptionRestController.setRestService(mockedRestService);
        Mockito.when(mockedRestService.buildUrlAndSendRequest(priorityQueueProperties,
                Mockito.any(LinkedMultiValueMap.class),
                StringUtils.EMPTY)).thenReturn(AdiClubMemberInfoDto.builder().email("email").build());
        Mockito.when(mockedRestService.buildUrlAndSendRequest(Mockito.any(PriorityQueueProperties.class),
                null,
                Mockito.any(AdiClubMemberInfoDto.class))).thenReturn(StringUtils.EMPTY);
        // TODO : Review, Mocking is not accurate here
    }


    @Test
    void addSubscriberToQueue() {
        ResponseEntity<String> response = subscriptionRestController.addSubscriberToQueue("test@gmail.com");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());



    }

}