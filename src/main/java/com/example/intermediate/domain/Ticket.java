package com.example.intermediate.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  // 도착 공항
  @Column(nullable = false)
  private String endPoint;
  // 출발 공항
  @Column(nullable = false)
  private String startPoint;
  // 항공기 편명
  @Column(nullable = false)
  private String flyNum;
  // 출발 시간
  @Column(nullable = false)
  private String startTime;
  // 여행 종료일
  @Column(nullable = false)
  private String endTime;
  // 소요시간
  @Column(nullable = false)
  private String takeTime;

  // 항공편 가격
  @Column(nullable = false)
  private int charge;

  @Column(nullable = false)
  private int bookingNum;

  @Column(nullable = false)
  private int days;


  // 여행 일자별 지출을 담고 있는 passenger의 리스트
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Passenger> passengerList = new ArrayList<>();

  //여행을 작성한 member
  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;
}
