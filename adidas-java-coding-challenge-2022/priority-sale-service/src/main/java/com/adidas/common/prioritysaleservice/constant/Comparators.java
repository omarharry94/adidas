package com.adidas.common.prioritysaleservice.constant;

import com.adidas.common.prioritysaleservice.job.EmailJob;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class Comparators {
    private static final Comparator<EmailJob> pointsComparator = Comparator.comparing(EmailJob::getPoints).reversed();
    private static final Comparator<EmailJob> ageOfSubscriptionComparator = Comparator.comparing(EmailJob::getRegistrationDate);
    private static final Comparator<EmailJob> emailComparison = Comparator.comparing(EmailJob::getEmail);

    public static final Comparator<EmailJob> EmailWinnerComparator = pointsComparator.thenComparing(ageOfSubscriptionComparator).thenComparing(emailComparison);
}
