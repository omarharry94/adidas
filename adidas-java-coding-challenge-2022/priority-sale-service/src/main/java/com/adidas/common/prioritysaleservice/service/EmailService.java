package com.adidas.common.prioritysaleservice.service;


import com.adidas.common.dto.AdiClubMemberInfoDto;
import com.adidas.common.prioritysaleservice.config.EmailServiceConfigProperties;
import com.adidas.common.prioritysaleservice.constant.Comparators;
import com.adidas.common.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.PriorityBlockingQueue;

@Service
public class EmailService {
    private RestService restService;
    private final PriorityBlockingQueue<AdiClubMemberInfoDto> priorityBlockingQueue;
    private final EmailServiceConfigProperties emailServiceConfigProperties;


    @Autowired
    public EmailService(@Autowired EmailServiceConfigProperties emailServiceConfigProperties,
                        @Value("queueSize") Integer queueSize) {

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
    public void sendEmailToWinner() throws InterruptedException {
        AdiClubMemberInfoDto take = this.priorityBlockingQueue.take();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        RestService restService;
        parameters.add("emailAddress", take.getEmail());
        Object block = this.restService.buildUrlAndSendRequest(
                this.emailServiceConfigProperties,
                parameters,
                ""
        ).block();

    }

}
