package com.example.intermediate.repository;


import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Passenger;
import com.example.intermediate.domain.RefreshToken;
import com.example.intermediate.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

//    List<Passenger> findAllTicket (Ticket ticket);
    Optional<Ticket> findByBookingNum (Ticket ticket);

}
