package com.adidas.common.prioritysaleservice.job;

import com.adidas.common.prioritysaleservice.constant.Comparators;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class EmailJobExecutor {
    private final ExecutorService priorityJobPoolExecutor;
    private final ExecutorService executorService
            = Executors.newSingleThreadExecutor();

    private final WinnerEmailScheduler winnerEmailScheduler;


    public EmailJobExecutor(Integer poolSize, Integer queueSize) {
        this.priorityJobPoolExecutor = Executors.newFixedThreadPool(poolSize);
        this.winnerEmailScheduler = new WinnerEmailScheduler(Comparators.EmailWinnerComparator, queueSize);
    }

    @Scheduled(cron = "0 * 9 * * ?")
    public void cronJobSch() {

    }
}







