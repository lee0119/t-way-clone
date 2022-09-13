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
  private String endPoint;
  private String endTime;
  private String startPoint;
  private String startTime;
  private String charge;
  private String flyNum;
  private String prestigeCharge;
  private String vihicleId;
}