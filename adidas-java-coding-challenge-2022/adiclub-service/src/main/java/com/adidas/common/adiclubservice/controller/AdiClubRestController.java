package com.adidas.common.adiclubservice.controller;

import com.adidas.common.dto.AdiClubMemberInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;


@RestController
@RequestMapping(value = "/adiclub")
public class AdiClubRestController {
  private static final Random RANDOM = new Random(System.nanoTime());
  private static final Integer RANDOM_MAX = 5000;

  @GetMapping
  public ResponseEntity<AdiClubMemberInfoDto> getAdiClubMemberInfo(
      @RequestParam(value = "emailAddress") final String emailAddress) {

      return sendAdiClubMemberInfoDtoResponseEntity(emailAddress);


  }

  private ResponseEntity<AdiClubMemberInfoDto> sendAdiClubMemberInfoDtoResponseEntity(String emailAddress) {
    return ResponseEntity
            .ok()
            .body(AdiClubMemberInfoDto
                    .builder()
                    .email(emailAddress)
                    .points(RANDOM.nextInt(RANDOM_MAX))
                    .registrationDate(Instant.now().minus(RANDOM.nextInt(365), ChronoUnit.DAYS))
                    .build()
            );
  }

  private ResponseEntity<AdiClubMemberInfoDto> setNotFoundAdiClubMemberInfoDtoResponseEntity() {
    return ResponseEntity
            .notFound()
            .build();
  }
}
