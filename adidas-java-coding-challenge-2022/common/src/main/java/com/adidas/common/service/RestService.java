package com.adidas.common.service;


import com.adidas.common.config.EndpointConfigProperties;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

public interface RestService {
    public Object buildUrlAndSendRequest(EndpointConfigProperties endpointConfigProperties,
                                               MultiValueMap<String, String> queryParameters,
                                               Object body);
}
