package com.example.intermediate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//Ticket 상세 조회용 DTO
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponseDto {
  private String airlineNm;
  private String arrAirportNm;
  private String arrPlandTime;
  private String depAirportNm;
  private String depPlandTime;
  private String economyCharge;
  private String prestigeCharge;
  private String vihicleId;
}