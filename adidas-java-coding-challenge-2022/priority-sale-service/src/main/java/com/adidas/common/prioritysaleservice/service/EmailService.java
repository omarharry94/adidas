package com.adidas.common.prioritysaleservice.service;



import com.adidas.common.prioritysaleservice.config.EmailServiceConfigProperties;
import com.adidas.common.prioritysaleservice.constant.Comparators;
import com.adidas.common.prioritysaleservice.dto.AdiClubMemberInfoDto;
import com.adidas.common.prioritysaleservice.utils.RestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Used to :
 *           1. send email request
 */
@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class EmailService {

    private final EmailServiceConfigProperties emailServiceConfigProperties;

    private PriorityBlockingQueue<AdiClubMemberInfoDto> priorityBlockingQueue;
    @Value("${spring.async.queue-capacity}")
    private int queueSize;

    @PostConstruct
    public void initialize() {
        this.priorityBlockingQueue = new PriorityBlockingQueue<>(queueSize, Comparators.EmailWinnerComparator);
    }


    public void putEmailJob(AdiClubMemberInfoDto emailJob) {
        this.priorityBlockingQueue.put(emailJob);
    }

    /**
     * @throws InterruptedException when cannot take element from priority queue
     */
    @Scheduled(cron = "${interval-in-cron}")
    @Async
    public void sendEmailToWinner() throws InterruptedException {
        if (!priorityBlockingQueue.isEmpty()){
            log.info("Sending email request");
            AdiClubMemberInfoDto take = this.priorityBlockingQueue.take();
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.add("emailAddress", take.getEmail());
            Object block = RestService.buildUrlAndSendRequest(
                    this.emailServiceConfigProperties,
                    parameters,
                    ""
            );
            log.info("Email request sent with: " + take);
        } else {
            log.info("Email queue is empty, maybe next time.");
        }

    }

}
