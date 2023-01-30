package com.adidas.common.prioritysaleservice.controller;


import com.adidas.common.dto.AdiClubMemberInfoDto;
import com.adidas.common.prioritysaleservice.config.EmailServiceConfigProperties;
import com.adidas.common.prioritysaleservice.service.EmailService;
import com.adidas.common.service.RestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@RestController
@RequiredArgsConstructor
public class PrioritySaleController {

    private final EmailService emailService;



    @PostMapping
    public ResponseEntity<String> addUserToQueue(@RequestBody final AdiClubMemberInfoDto adiClubMemberInfoDto) {
        this.emailService.putEmailJob(adiClubMemberInfoDto);
        return ResponseEntity
                .ok()
                .body("");
    }
}
