package com.adidas.common.prioritysaleservice.service;


import com.adidas.common.dto.AdiClubMemberInfoDto;
import com.adidas.common.prioritysaleservice.config.EmailServiceConfigProperties;
import com.adidas.common.prioritysaleservice.constant.Comparators;
import com.adidas.common.service.RestService;
import com.adidas.common.service.impl.RestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.PriorityBlockingQueue;

@Component
@EnableAsync
public class EmailService {
    private static final RestService restService = new RestServiceImpl();
    private final PriorityBlockingQueue<AdiClubMemberInfoDto> priorityBlockingQueue;
    private final EmailServiceConfigProperties emailServiceConfigProperties;


    @Autowired
    public EmailService(@Autowired EmailServiceConfigProperties emailServiceConfigProperties,
                        @Value("${spring.async.queue-capacity}") int queueSize) {

        this(new PriorityBlockingQueue<>(queueSize, Comparators.EmailWinnerComparator),
                emailServiceConfigProperties);
    }

    private EmailService(PriorityBlockingQueue<AdiClubMemberInfoDto> priorityBlockingQueue, EmailServiceConfigProperties emailServiceConfigProperties) {
        this.priorityBlockingQueue = priorityBlockingQueue;
        this.emailServiceConfigProperties = emailServiceConfigProperties;
    }


    public void putEmailJob(AdiClubMemberInfoDto emailJob) {
        this.priorityBlockingQueue.put(emailJob);
    }

    @Scheduled(cron = "${interval-in-cron}")
    @Async
    public void sendEmailToWinner() throws InterruptedException {
        if (!priorityBlockingQueue.isEmpty()){
            AdiClubMemberInfoDto take = this.priorityBlockingQueue.take();
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.add("emailAddress", take.getEmail());
            Object block = restService.buildUrlAndSendRequest(
                    this.emailServiceConfigProperties,
                    parameters,
                    ""
            ).block();
        }

    }

}
