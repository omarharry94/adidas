package com.adidas.common.prioritysaleservice.utils;

import com.adidas.common.prioritysaleservice.config.EndpointConfigProperties;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RestServiceTest {

    @Test
    void buildUrlAndSendRequest_validInput_returnsExpectedObject() {
        EndpointConfigProperties endpointConfigProperties = new EndpointConfigProperties();
        endpointConfigProperties.setUrl("http://localhost:8080");
        endpointConfigProperties.setPath("/dummy");
        endpointConfigProperties.setMethod(HttpMethod.GET);

        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        Object body = new Object();

        WebClient webClient = mock(WebClient.class);
        WebClient.RequestBodyUriSpec requestBodySpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        Object expectedResult = new Object();

        when(webClient.method(any(HttpMethod.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.uri(any(URI.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Mockito.any(Class.class))).thenReturn(Mono.just(expectedResult));

        RestService restService = new RestService();
        restService.webClient = webClient;

        Object result = restService.buildUrlAndSendRequest(endpointConfigProperties, queryParameters, body);

        assertEquals(expectedResult, result);
        verify(webClient, times(1)).method(HttpMethod.GET);
        verify(requestBodySpec, times(1)).uri(any(URI.class));
        verify(requestBodySpec, times(1)).headers(any());
        verify(requestBodySpec, times(1)).retrieve();
        verify(responseSpec, times(1)).onStatus(any(), any());
        verify(responseSpec, times(1)).bodyToMono(Object.class);
    }
}