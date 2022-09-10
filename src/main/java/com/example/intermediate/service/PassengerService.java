//package com.example.intermediate.service;
//
//
//import com.example.intermediate.dto.response.PassengerResponseDto;
//import com.example.intermediate.dto.response.ResponseDto;
//import com.example.intermediate.domain.Passenger;
//import com.example.intermediate.domain.Ticket;
//import com.example.intermediate.repository.PassengerRepository;
//import com.example.intermediate.repository.TicketRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class PassengerService {
//
//  private final PassengerRepository passengerRepository;
//  private final TicketRepository ticketRepository;
//
//  /*
//  * passenger 생성 메서드
//  * 입력받은 ticket에 days 만큼의 passenger를 만들어 passengerList를 생성한다.
//  * */
//  @Transactional
//  public void createPassenger(Ticket ticket) {
//    List<Passenger> passengerList = new ArrayList<>();
//
//    //해당 ticket의 cost가 없는 passenger를 days만큼 생성하여 passengerList에 더한다.
//    for (int i = 0; i < 5; i++) {
//      Passenger passenger = Passenger.builder()
//        .ticket(ticket)
//        .subTotal(0)
//        .build();
//      passengerList.add(passenger);
//    }
//    passengerRepository.saveAll(passengerList);
//  }
//
//  //  booking 조회
//  @Transactional(readOnly = true)
//  public ResponseDto<?> getTicket(Long id) {
//    Ticket ticket = isPresentTicket(id);
//    if (null == ticket) { //해당하는 id의 passenger가 반환되지 않았으면 fail 반환
//      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 passenger id 입니다.");
//    }
//
//
//    return ResponseDto.success(
//            PassengerResponseDto.builder()
//                    .bookingNum(ticket.getBookingNum())
//                    .build()
//    );
//  }
//
//  //ticket id에 해당하는
//  @Transactional(readOnly = true)
//  public ResponseDto<?> getAllPassenger(Long ticketId) {
//
//    Ticket ticket = isPresentTicket(ticketId);
//    if(null == ticket){ //ticketId에 해당하는 ticket이 없으면 fail
//      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 여행 id 입니다.");
//    }
//
//    //ticket id에 해당하는 passenger를 id 순으로 담은 List
//    List<Passenger> passengerList = passengerRepository.findAllByTicketOrderById(ticket);
//    List<PassengerResponseDto> dtoList = new ArrayList<>();
//
//    for (Passenger passenger:passengerList) {
//
//      //passenger 정보를 담고있고 위에서 만든 costList 담은 dto 생성 후 passengerList에 add
//      dtoList.add(
//              PassengerResponseDto.builder()
//                      .id(passenger.getId())
//                      .subTotal(passenger.getSubTotal())
//                      .build());
//    }
//    return ResponseDto.success(dtoList);
//  }
//
//  //입력받은 id에 해당하는 passenger 존재하면 passenger 반환, 없으면 null
//  @Transactional(readOnly = true)
//  public Passenger isPresentPassenger(Long id) {
//    Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
//    return optionalPassenger.orElse(null);
//  }
//
//  //입력받은 id에 해당하는 ticket 존재하면 ticket 반환, 없으면 null
//  @Transactional(readOnly = true)
//  public Ticket isPresentTicket(Long id) {
//    Optional<Ticket> optionalTicket = ticketRepository.findById(id);
//    return optionalTicket.orElse(null);
//  }
//
//  //cost 추가 혹은 삭제에 의해 비용 변경시 subTotal 갱신
//  public void uppassenger(int pay, Long passengerId){
//    Passenger passenger = isPresentPassenger(passengerId);
//    passenger.uppassenger(pay);
//  }
//
//}
