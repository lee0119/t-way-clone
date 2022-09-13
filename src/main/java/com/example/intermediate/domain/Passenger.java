package com.example.intermediate.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 여행의 passenger.
// ex) 2박 3일의 여행 일정인 경우 1일차 passenger, 2일차 passenger, 3일차 passenger 총 3개의 passenger를 가지고 있다.
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Passenger extends Timestamped {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String gender;

  @Column(nullable = false)
  private String country;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String birth;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String number;

  @JoinColumn(name = "ticket_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Ticket ticket;
}



