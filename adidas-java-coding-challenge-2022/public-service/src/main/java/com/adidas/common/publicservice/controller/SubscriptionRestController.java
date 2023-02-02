package com.adidas.common.publicservice.controller;


import com.adidas.common.publicservice.config.AdiClubConfigProperties;
import com.adidas.common.publicservice.config.PriorityQueueProperties;
import com.adidas.common.publicservice.dto.AdiClubMemberInfoDto;
import com.adidas.common.publicservice.utils.RestService;
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

/**
 * RestController that handles adding new email addresses to the priority service queue.
 *
 * @author  oamr.bakhtaoui
 */
@Slf4j
@RestController
@RequestMapping(value = "/subscription")
@RequiredArgsConstructor
public class SubscriptionRestController {


    private final AdiClubConfigProperties adiClubConfigProperties;
    private final PriorityQueueProperties priorityQueueProperties;


    /**
     * This is a Rest controller written in Java that implements a subscription endpoint.
     * When the endpoint is called with an email address as a query parameter, the code makes
     * a call to the adidas Club API to retrieve information about the member with the given
     * email. If the member is found, the code then sends the member's information to the priority
     * queue service. If the member is not found in the adidas Club API, the endpoint returns a 404
     * Not Found response. If the call to the priority queue service is successful
     * , the endpoint returns a 200 OK response.
     *
     * @author omar.bakhtaoui
     *
     *
     * @param emailAddress email address needed for subscription
     * @return 201(Created) when the subscription takes place successfully
     *         404(Not found) when the subscriber does not exist in AdiClub
     *         409(Already exists) when the subscriber already exits in the queue
     */
    @Operation(summary = "Adds an Adi Member to the priority queue for sending win emails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adi member found and added to the queue successfully",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Adi member already exists in the queue",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Adi member not found",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<String> addSubscriberToQueue(@RequestParam("emailAddress") @Email String emailAddress) {

        ObjectMapper oMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.add("emailAddress", emailAddress);
        log.info("Calling Adi Club with: " + adiClubConfigProperties.toString());
        Object adiClubResponse = RestService.buildUrlAndSendRequest(adiClubConfigProperties,
                queryParameters,
                StringUtils.EMPTY);

        AdiClubMemberInfoDto adiClubMemberInfoDto = oMapper.convertValue(adiClubResponse, AdiClubMemberInfoDto.class);
        if (adiClubMemberInfoDto != null){
            log.info("Calling priority queue with: " + priorityQueueProperties.toString());

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
