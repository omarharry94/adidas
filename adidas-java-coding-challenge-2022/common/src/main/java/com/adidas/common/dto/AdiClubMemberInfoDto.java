package com.adidas.common.dto;

import lombok.*;

import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString()
public class AdiClubMemberInfoDto {
  private String email;
  private Integer points;
  private Instant registrationDate;

  public AdiClubMemberInfoDto(String email, Integer points, Instant registrationDate) {
    this.email = email;
    this.points = points;
    this.registrationDate = registrationDate;
  }
}
