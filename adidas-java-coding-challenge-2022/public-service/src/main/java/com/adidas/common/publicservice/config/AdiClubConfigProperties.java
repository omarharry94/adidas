package com.adidas.common.publicservice.config;


import com.adidas.common.config.EndpointConfigProperties;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("adiclub")
@RequiredArgsConstructor
public class AdiClubConfigProperties extends EndpointConfigProperties {

}
