package com.adidas.common.publicservice.config;


import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ToString(callSuper = true)
@Component
@ConfigurationProperties("priority-queue")
public class PriorityQueueProperties  extends EndpointConfigProperties {
}
