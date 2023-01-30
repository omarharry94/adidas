package com.adidas.common.prioritysaleservice.config;


import com.adidas.common.config.EndpointConfigProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Endpoint configuration properties for email sending
 *
 * @author omar.bakhtaoui
 */
@Component
@ConfigurationProperties("email")
public class EmailServiceConfigProperties extends EndpointConfigProperties {

}
