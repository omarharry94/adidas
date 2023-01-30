package com.adidas.common.publicservice.controller;



import com.adidas.common.dto.AdiClubMemberInfoDto;
import com.adidas.common.publicservice.config.AdiClubConfigProperties;
import com.adidas.common.service.RestService;
import lombok.RequiredArgsConstructor;

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


@RestController
@RequestMapping(value = "/subscription")
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class SubscriptionRestController {


    private final AdiClubConfigProperties adiClubConfigProperties;
    private final RestService restService;

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

        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.add("emailAddress", emailAddress);
        Mono<Object> adiClubResponse = restService.buildUrlAndSendRequest(adiClubConfigProperties,
                queryParameters,
                StringUtils.EMPTY);

        AdiClubMemberInfoDto adiClubMemberInfoDto = (AdiClubMemberInfoDto) adiClubResponse.block();

        return ResponseEntity
                .ok()
                .body("");
    }




}
