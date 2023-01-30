package com.adidas.common.emailservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/dummyEmail")
public class DummyEmailRestController {

  private static final String DUMMY_RESPONSE = "Hello, this is a dummy response from public service";

  @PostMapping
  public ResponseEntity<String> getDummyEndpointResponse(@RequestParam String emailAddress) {
    log.info("sending email to " + emailAddress);
    return ResponseEntity
            .ok()
            .build();
  }

}
