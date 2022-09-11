package com.example.intermediate.service;


import com.example.intermediate.domain.Member;
import com.example.intermediate.dto.request.PassengerRequestDto;
import com.example.intermediate.dto.response.PassengerResponseDto;
import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.domain.Passenger;
import com.example.intermediate.domain.Ticket;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.PassengerRepository;
import com.example.intermediate.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerService {

  private final TokenProvider tokenProvider;
  private final PassengerRepository passengerRepository;
  private final TicketRepository ticketRepository;

  /*
  * passenger 생성 메서드
  * 입력받은 ticket에 days 만큼의 passenger를 만들어 passengerList를 생성한다.
  * */



    @Transactional
    public ResponseDto<?> createPassenger(PassengerRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }


        Ticket ticket = TicketService.isPresentPost(requestDto.?? ());
        if (null == ticket) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        Passenger passenger = Passenger.builder()
                .ticket(ticket)
             
                .bookingNum(requestDto.getBookingNum())

                .build();
        passengerRepository.save(passenger);

        return ResponseDto.success(
                PassengerResponseDto.builder()
                        .id(passenger.getId())
                        .country(passenger.getCountry())
                        .name(passenger.getName())
                        .name(passenger.getName())
                        .birth(passenger.getBirth())
                        .email(passenger.getEmail())
                        .number(passenger.getNumber())
                        .build()
        );
    }
  //  booking 조회
  @Transactional(readOnly = true)
  public ResponseDto<?> getBookingNum (Long ) {
      Ticket Ticket = TicketService.isPresentPost(requestDto, );
      if (null == Ticket) {
          return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
      }

      List<Passenger> passengerList = passengerRepository.findAllByTicket(ticket);
      List<PassengerResponseDto> commentResponseDtoList = new ArrayList<>();

      for (Passenger passenger : passengertList) {

          passengerResponseDtoList.add(
                  passengerResponseDto.builder()
                          .id(passenger.getId())
                          .country(passenger.getCountry())
                          .name(passenger.getName())
                          .name(passenger.getName())
                          .birth(passenger.getBirth())
                          .email(passenger.getEmail())
                          .number(passenger.getNumber())
                          .build()
          );
      }
      return ResponseDto.success(commentResponseDtoList);
  }

}