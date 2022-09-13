package com.example.intermediate.controller;

import com.example.intermediate.domain.Passenger;
import com.example.intermediate.dto.request.MemberRequestDto;
import com.example.intermediate.dto.request.PassengerRequestDto;
import com.example.intermediate.dto.request.TicketRequestDto;
import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.service.PassengerService;
import com.example.intermediate.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TicketController {

    private final TicketService ticketService;

    //여행 상세 페이지 조회하기
    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    public ResponseDto<?> SearchTicket () throws IOException, ParseException {
        return ticketService.SearchTicket();
    }

    //티켓 정보 입력
    @RequestMapping(value = "/api/auth/booking", method = RequestMethod.POST)
    public ResponseDto<?> creatTicket(@RequestBody TicketRequestDto requestDto,HttpServletRequest request) {
        return ticketService.createTicket(requestDto,request);
    }

    //나의 예약 조회하기
    @RequestMapping(value = "/api/auth/mybooking", method = RequestMethod.GET)
    public ResponseDto<?> getTicket () throws IOException, ParseException {
        return ticketService.getTicket();
    }

    //여행 상세 페이지 조회하기



    }

//  //여행 목록 조회하기
//  @RequestMapping(value = "/ticket", method = RequestMethod.GET)
//  public ResponseDto<?> getAllTickets(HttpServletRequest request) {
//    return ticketService.getAllTicket(request);
//  }



//여행 삭제하기
//  @RequestMapping(value = "/ticket/{id}", method = RequestMethod.DELETE)
//  public ResponseDto<?> deleteTicket(@PathVariable Long id,
//                                   HttpServletRequest request) {
//    return ticketService.deleteTicket(id, request);
//  }

