package com.adidas.common.publicservice.config;


import com.adidas.common.config.EndpointConfigProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("adiclub")
public class AdiClubConfigProperties extends EndpointConfigProperties {

}
