package com.adidas.common.prioritysaleservice.controller;


import com.adidas.common.dto.AdiClubMemberInfoDto;
import com.adidas.common.prioritysaleservice.config.EmailServiceConfigProperties;
import com.adidas.common.prioritysaleservice.service.EmailService;
import com.adidas.common.service.RestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
@Slf4j
@RestController
@RequestMapping("/addUserToQueue")
@RequiredArgsConstructor
public class PrioritySaleController {

    private final EmailService emailService;



    @PostMapping
    public ResponseEntity<String> addUserToQueue(@RequestBody final AdiClubMemberInfoDto adiClubMemberInfoDto) {
        String message = "Putting email in queue of: ";
        log.info(message +  String.valueOf(adiClubMemberInfoDto));
        this.emailService.putEmailJob(adiClubMemberInfoDto);
        return ResponseEntity
                .ok()
                .build();
    }
}
