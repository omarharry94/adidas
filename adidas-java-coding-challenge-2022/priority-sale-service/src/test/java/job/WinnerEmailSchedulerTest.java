package job;

import com.adidas.common.prioritysaleservice.job.EmailJob;
import com.adidas.common.prioritysaleservice.job.WinnerEmailScheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class WinnerEmailSchedulerTest {

    @Test
    void scheduleJob() {
        Comparator<EmailJob> pointsComparing = Comparator.comparing(EmailJob::getPoints).reversed();
        Comparator<EmailJob> pointsCompareAndThenDateCompare = pointsComparing.thenComparing(EmailJob::getRegistrationDate);

        Instant now = Instant.now();
        EmailJob emailJob1 = new EmailJob("test1@test.com", 1000, now.minus(1, ChronoUnit.DAYS));
        EmailJob emailJob2 = new EmailJob("test2@test.com", 1000, now.minus(2, ChronoUnit.DAYS));
        EmailJob emailJob3 = new EmailJob("test3@test.com", 500, now.minus(1, ChronoUnit.DAYS));
        EmailJob emailJob4 = new EmailJob("test4@test.com", 500, now.minus(2, ChronoUnit.DAYS));

        List<EmailJob> emailJobs = Arrays.asList(emailJob1, emailJob2, emailJob3, emailJob4);
        emailJobs.sort(pointsCompareAndThenDateCompare);
        Assertions.assertFalse(emailJobs.isEmpty());
    }

    @Test
    void execute() {
    }


    private static int POOL_SIZE = 1;
    private static int QUEUE_SIZE = 10;

    @Test
     void whenMultiplePriorityJobsQueued_thenHighestPriorityJobIsPicked() {
        Instant now = Instant.now();
        EmailJob emailJob1 = new EmailJob("test1@test.com", 1000, now.minus(1, ChronoUnit.DAYS));
        EmailJob emailJob2 = new EmailJob("test2@test.com", 1000, now.minus(2, ChronoUnit.DAYS));
        EmailJob emailJob3 = new EmailJob("test3@test.com", 500, now.minus(1, ChronoUnit.DAYS));
        EmailJob emailJob4 = new EmailJob("test4@test.com", 500, now.minus(2, ChronoUnit.DAYS));

        WinnerEmailScheduler pjs = new WinnerEmailScheduler(
                POOL_SIZE, QUEUE_SIZE);

        pjs.scheduleEmailJob(emailJob1);
        pjs.scheduleEmailJob(emailJob2);
        pjs.scheduleEmailJob(emailJob3);
        pjs.scheduleEmailJob(emailJob4);


        pjs.execute();
        pjs.execute();
        pjs.execute();
        pjs.execute();








        // clean up
    }
}