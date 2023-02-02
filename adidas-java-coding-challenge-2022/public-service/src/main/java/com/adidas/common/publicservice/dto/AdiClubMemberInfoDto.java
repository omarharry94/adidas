package com.adidas.common.publicservice.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString()
public class AdiClubMemberInfoDto {
  private String email;
  private Integer points;
  private Instant registrationDate;

}
