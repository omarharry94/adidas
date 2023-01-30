package com.adidas.common.publicservice.controller;



import com.adidas.common.dto.AdiClubMemberInfoDto;
import com.adidas.common.publicservice.config.AdiClubConfigProperties;
import com.adidas.common.publicservice.config.PriorityQueueProperties;
import com.adidas.common.service.RestService;
import com.adidas.common.service.impl.RestServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Email;

@Slf4j
@RestController
@RequestMapping(value = "/subscription")
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class SubscriptionRestController {


    private final AdiClubConfigProperties adiClubConfigProperties;
    private final PriorityQueueProperties priorityQueueProperties;
    private static RestService restService = new RestServiceImpl();

    /**
     * Adds new email belonging to an adiclub member to the priority service queue
     *
     *
     * @param emailAddress email address needed for subscription
     * @return 201(Created) when the subscription takes place successfully
     *         404(Not found) when the subscriber does not exist in adiclub
     *         409(Already exists) when the subscriber already exits in the queue
     */
    @PostMapping
    public ResponseEntity<String> addSubscriberToQueue(@RequestParam("emailAddress") @Email String emailAddress) {

        ObjectMapper oMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.add("emailAddress", emailAddress);
        log.info("Calling Adi Club");
        Mono<Object> adiClubResponse = restService.buildUrlAndSendRequest(adiClubConfigProperties,
                queryParameters,
                StringUtils.EMPTY);

        AdiClubMemberInfoDto adiClubMemberInfoDto = oMapper.convertValue(adiClubResponse.block(), AdiClubMemberInfoDto.class);
        if (adiClubMemberInfoDto != null){
            log.info("Calling priority queue");

            restService.buildUrlAndSendRequest(priorityQueueProperties,
                    null,
                    adiClubMemberInfoDto).block();
        }
        return ResponseEntity
                .ok()
                .body("");
    }




}
