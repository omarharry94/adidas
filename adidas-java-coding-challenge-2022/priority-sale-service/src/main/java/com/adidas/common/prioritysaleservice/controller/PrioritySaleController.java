package com.adidas.common.prioritysaleservice.controller;


import com.adidas.common.prioritysaleservice.config.EmailServiceConfigProperties;
import com.adidas.common.service.RestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PrioritySaleController {

    private final EmailServiceConfigProperties emailServiceConfigProperties;
    private final RestService restService;



    @GetMapping
    public ResponseEntity<String> addUserToQueue(@RequestParam("emailAddress") final String emailAddress) {
        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.add("emailAddress", emailAddress);





        return ResponseEntity
                .ok()
                .body(DUMMY_RESPONSE);
    }
}
