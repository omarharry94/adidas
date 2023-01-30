package com.adidas.common.publicservice.controller;



import com.adidas.common.dto.AdiClubMemberInfoDto;
import com.adidas.common.publicservice.config.AdiClubConfigProperties;
import com.adidas.common.publicservice.config.PriorityQueueProperties;
import com.adidas.common.utils.RestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import javax.validation.constraints.Email;

@Slf4j
@RestController
@RequestMapping(value = "/subscription")
@RequiredArgsConstructor
public class SubscriptionRestController {


    private final AdiClubConfigProperties adiClubConfigProperties;
    private final PriorityQueueProperties priorityQueueProperties;




    /**
     * Adds new email belonging to an adiclub member to the priority service queue
     *
     *
     * @param emailAddress email address needed for subscription
     * @return 201(Created) when the subscription takes place successfully
     *         404(Not found) when the subscriber does not exist in adiclub
     *         409(Already exists) when the subscriber already exits in the queue
     */
    @Operation(summary = "Adds an Adi Member to send win email ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the adiMember and queued",
                            content = @Content),
            @ApiResponse(responseCode = "409", description = "Already existing in queue",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Adi Member not found",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<String> addSubscriberToQueue(@RequestParam("emailAddress") @Email String emailAddress) {

        ObjectMapper oMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.add("emailAddress", emailAddress);
        log.info("Calling Adi Club");
        Object adiClubResponse = RestService.buildUrlAndSendRequest(adiClubConfigProperties,
                queryParameters,
                StringUtils.EMPTY);

        AdiClubMemberInfoDto adiClubMemberInfoDto = oMapper.convertValue(adiClubResponse, AdiClubMemberInfoDto.class);
        if (adiClubMemberInfoDto != null){
            log.info("Calling priority queue");

            RestService.buildUrlAndSendRequest(priorityQueueProperties,
                    null,
                    adiClubMemberInfoDto);
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity.notFound().build();

    }




}
