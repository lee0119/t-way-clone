package com.example.intermediate.dto.request;

import com.example.intermediate.dto.response.PassengerResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDto {
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

  private List<PassengerRequestDto> passengerList = new ArrayList<>();


}