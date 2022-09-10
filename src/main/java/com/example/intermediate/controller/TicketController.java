package com.example.intermediate.controller;

import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class TicketController {

  private final TicketService ticketService;

  //여행 생성하기
  @RequestMapping(value = "/ticket", method = RequestMethod.POST)
  public ResponseDto<?> createTicket(HttpServletRequest request) {
    return ticketService.createTicket( request);
  }

//  //여행 목록 조회하기
//  @RequestMapping(value = "/ticket", method = RequestMethod.GET)
//  public ResponseDto<?> getAllTickets(HttpServletRequest request) {
//    return ticketService.getAllTicket(request);
//  }

  //여행 상세 페이지 조회하기
  @RequestMapping(value = "/ticket", method = RequestMethod.GET)
  public ResponseDto<?> getTicket() throws IOException, ParseException {
    return ticketService.getTicket();
  }

  //여행 삭제하기
  @RequestMapping(value = "/ticket/{id}", method = RequestMethod.DELETE)
  public ResponseDto<?> deleteTicket(@PathVariable Long id,
                                   HttpServletRequest request) {
    return ticketService.deleteTicket(id, request);
  }
}
