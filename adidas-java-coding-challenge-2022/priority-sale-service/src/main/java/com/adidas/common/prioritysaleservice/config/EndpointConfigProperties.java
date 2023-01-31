package com.adidas.common.prioritysaleservice.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 * Endpoint configuration properties
 *
 * @author omar.bakhtaoui
 */

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
