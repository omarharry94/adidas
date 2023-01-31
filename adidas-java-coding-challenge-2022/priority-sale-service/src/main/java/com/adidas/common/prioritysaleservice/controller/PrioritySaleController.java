package com.adidas.common.prioritysaleservice.controller;


import com.adidas.common.prioritysaleservice.dto.AdiClubMemberInfoDto;
import com.adidas.common.prioritysaleservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Priority sales controller
 *
 * @author omar.bakhtaoui
 */
@Slf4j
@RestController
@RequestMapping("/addUserToQueue")
@RequiredArgsConstructor
public class PrioritySaleController {

    private final EmailService emailService;


    /**
     * @param adiClubMemberInfoDto adi club member to send email to
     * @return 200(OK) response entity if adi club member was prepared for email sending
     *
     * @author omar.bakhtaoui
     */
    @PostMapping
    public ResponseEntity<String> addUserToQueue(@RequestBody final AdiClubMemberInfoDto adiClubMemberInfoDto) {
        String message = "Putting email in queue of: ";
        log.info(message +  adiClubMemberInfoDto);
        this.emailService.putEmailJob(adiClubMemberInfoDto);
        return ResponseEntity
                .ok()
                .build();
    }
}
