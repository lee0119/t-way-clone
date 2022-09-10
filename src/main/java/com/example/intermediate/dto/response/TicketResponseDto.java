package com.example.intermediate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

//Ticket 상세 조회용 DTO
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDto {
  private Long id;
  private String endPoint;
  private String startPoint;
  private String flyNum;
  private String startTime;
  private String endTime;
  private String takeTime;
  private int charge;

  private List<PassengerResponseDto> passengerList = new ArrayList<>();
}
