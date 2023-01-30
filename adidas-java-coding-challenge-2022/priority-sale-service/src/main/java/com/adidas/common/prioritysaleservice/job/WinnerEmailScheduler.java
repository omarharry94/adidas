package com.adidas.common.prioritysaleservice.job;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;


@Getter
@Setter
public class WinnerEmailScheduler {

    private final PriorityBlockingQueue<EmailJob> priorityQueue;

    public WinnerEmailScheduler(Comparator<EmailJob> comparator, Integer queueSize) {
        this.priorityQueue = new PriorityBlockingQueue<>(queueSize, comparator);
    }

    public void scheduleEmailJob(EmailJob emailJob) {
        this.priorityQueue.put(emailJob);
    }

    public EmailJob removeEmailJob() throws InterruptedException {
        return this.priorityQueue.take();
    }


}