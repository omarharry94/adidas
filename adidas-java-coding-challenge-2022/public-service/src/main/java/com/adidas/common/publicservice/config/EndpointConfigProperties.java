package com.adidas.common.publicservice.config;


import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 * Endpoint configuration properties
 *
 * @author omar.bakhtaoui
 */

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class EndpointConfigProperties {
    /**
     * Url of the endpoint we are calling
     */
    private String url;
    /**
     * Path of the endpoint we are calling
     */
    private String path;
    /**
     * method of the rest call we are making
     */
    private HttpMethod method;
    /**
     * headers
     */
    private HttpHeaders headers;
}
