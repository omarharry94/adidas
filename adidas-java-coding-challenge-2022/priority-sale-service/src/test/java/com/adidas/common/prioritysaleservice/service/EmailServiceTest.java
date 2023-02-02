package com.adidas.common.prioritysaleservice.service;

import com.adidas.common.prioritysaleservice.config.EmailServiceConfigProperties;
import com.adidas.common.prioritysaleservice.dto.AdiClubMemberInfoDto;
import com.adidas.common.prioritysaleservice.utils.RestService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

import static org.mockito.Mockito.*;

public class EmailServiceTest {

    private static final Random RANDOM = new Random(System.nanoTime());
    private static final Integer RANDOM_MAX = 5000;

    @ParameterizedTest
    @ValueSource(strings =
            {"user1@gmail.com", "user2@gmail.com", "user3@adiclub.com", "user4@adiclub.com"})
    public void sendEmailToWinnerTest(String emailAddress) throws InterruptedException {
        PriorityBlockingQueue<AdiClubMemberInfoDto> priorityBlockingQueue = mock(PriorityBlockingQueue.class);
        AdiClubMemberInfoDto adiClubMemberInfoDto = AdiClubMemberInfoDto
                .builder()
                .email(emailAddress)
                .points(RANDOM.nextInt(RANDOM_MAX))
                .registrationDate(Instant.now().minus(RANDOM.nextInt(365), ChronoUnit.DAYS))
                .build();
        EmailServiceConfigProperties emailServiceConfigProperties = new EmailServiceConfigProperties();
        emailServiceConfigProperties.setUrl("http://localhost:8080");
        emailServiceConfigProperties.setPath("/dummy");
        emailServiceConfigProperties.setMethod(HttpMethod.GET);

        when(priorityBlockingQueue.isEmpty()).thenReturn(false);
        when(priorityBlockingQueue.take()).thenReturn(adiClubMemberInfoDto);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("emailAddress", adiClubMemberInfoDto.getEmail());

        EmailService emailService = new EmailService(emailServiceConfigProperties);
        emailService.queueSize = 100;
        emailService.initialize();

        try (MockedStatic<RestService> utilities = Mockito.mockStatic(RestService.class)) {

            utilities.when(() -> RestService.buildUrlAndSendRequest(Mockito.eq(emailServiceConfigProperties),
                    Mockito.eq(parameters),
                    Mockito.eq(""))).thenReturn("someAnswer");


            emailService.putEmailJob(adiClubMemberInfoDto);

            emailService.sendEmailToWinner();

            MockedStatic.Verification verification = () -> RestService.buildUrlAndSendRequest(
                    emailServiceConfigProperties,
                    parameters,
                    ""
            );
            utilities.verify(verification, times(1));
        }
    }
}