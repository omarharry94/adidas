package com.adidas.common.emailservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/dummyEmail")
public class DummyEmailRestController {

  private static final String DUMMY_RESPONSE = "Hello, this is a dummy response from public service";

  @GetMapping
  public ResponseEntity<String> getDummyEndpointResponse(@RequestParam String emailAddress) {
    log.info("sending email");
    return ResponseEntity
        .ok()
        .body(DUMMY_RESPONSE);
  }

}
