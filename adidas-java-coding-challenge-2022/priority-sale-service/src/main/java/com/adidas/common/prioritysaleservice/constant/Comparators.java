package com.adidas.common.prioritysaleservice.constant;


import com.adidas.common.prioritysaleservice.dto.AdiClubMemberInfoDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Comparator;


/**
 * Final class for more comparators if needed
 *
 * @author omar.bakhtaoui
 */
@NoArgsConstructor(access = AccessLevel.NONE)
public final class Comparators {
    private static final Comparator<AdiClubMemberInfoDto> pointsComparator = Comparator.comparing(AdiClubMemberInfoDto::getPoints).reversed();
    private static final Comparator<AdiClubMemberInfoDto> ageOfSubscriptionComparator = Comparator.comparing(AdiClubMemberInfoDto::getRegistrationDate);
    private static final Comparator<AdiClubMemberInfoDto> emailComparison = Comparator.comparing(AdiClubMemberInfoDto::getEmail);

    public static final Comparator<AdiClubMemberInfoDto> EmailWinnerComparator = pointsComparator.thenComparing(ageOfSubscriptionComparator).thenComparing(emailComparison);
}
