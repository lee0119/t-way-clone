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
  // 도착 공항
  private String endPoint;
  // 출발 공항
  private String startPoint;
  // 항공기 편명
  private String flyNum;
  // 출발 시간
  private String startTime;
  // 여행 종료일
  private String endTime;
  // 소요시간
  private String takeTime;
  // 항공편 가격
  private int charge;
}
