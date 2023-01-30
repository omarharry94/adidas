package com.adidas.common.utils;


import com.adidas.common.config.EndpointConfigProperties;
import com.adidas.common.exception.WebClientErrorHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.NONE)
public class RestService {

    private static final WebClient webClient = WebClient.create();

    public static Object buildUrlAndSendRequest(EndpointConfigProperties endpointConfigProperties,
                                               MultiValueMap<String, String> queryParameters,
                                               Object body) {
        URI endpointUrl = UriComponentsBuilder
                .fromPath(endpointConfigProperties.getPath())
                .uri(URI.create(endpointConfigProperties.getUrl()))
                // Setting request parameters
                .queryParams(queryParameters)
                .build()
                // Getting uri
                .toUri();

        // Builds request
        WebClient.RequestBodySpec request = webClient
                .method(endpointConfigProperties.getMethod())
                .uri(endpointUrl)
                .headers(httpHeaders -> httpHeaders.addAll(
                        Optional.ofNullable(endpointConfigProperties.getHeaders()).orElse(new HttpHeaders())));
        // Selects the way the web client will be used having in count the method
        WebClient.ResponseSpec retrieve = HttpMethod.GET == endpointConfigProperties.getMethod()
                ? request.retrieve()
                : request.body(BodyInserters.fromValue(body)).retrieve();
        return retrieve
                .onStatus(HttpStatus::isError, WebClientErrorHandler::manageError)
                .bodyToMono(Object.class)
                .block();
    }


}