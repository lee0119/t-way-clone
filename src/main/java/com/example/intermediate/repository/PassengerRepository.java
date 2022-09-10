package com.example.intermediate.repository;


import com.example.intermediate.domain.Passenger;
import com.example.intermediate.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
  List<Passenger> findAllByTicket(Ticket ticket);
//  void deleteAllByTicket(Ticket ticket);
//  List<Passenger> findAllByTicketOrderById(Ticket ticket);
}
