package com.example.intermediate.service;


import com.example.intermediate.dto.request.TicketRequestDto;
import com.example.intermediate.domain.Passenger;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Ticket;
import com.example.intermediate.dto.response.*;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.PassengerRepository;
import com.example.intermediate.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.h2.util.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

  private final TokenProvider tokenProvider;
  private final TicketRepository ticketRepository;
  private final PassengerRepository passengerRepository;
  private final PassengerService passengerService;

  //ticket 생성 메서드
  @Transactional
  public ResponseDto<?> createTicket(TicketRequestDto requestDto, HttpServletRequest request) {

    //토큰 유효성 검사, 유효하면 해당 member 가져옴 아니면 null
    Member member = isValipassengerAccess(request);
    if (null == member) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    //dto에 담긴 정보로 Ticket 생성
    Ticket ticket = Ticket.builder().build();

    ticketRepository.save(ticket);

    return ResponseDto.success(ticket);
  }

  // 입력받은 id의 ticket 상세 정보가 담긴 dto 반환 메서드
  @Transactional(readOnly = true)
  public ResponseDto<?> getTicket() throws IOException {
    StringBuilder urlBuilder = new StringBuilder("http://openapi.airport.co.kr/service/rest/AirportCodeList/getAirportCodeList"); /*URL*/
    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "Zt6RrqFpINSOd/H6T+spf7QTmDLyQax2Ndj5BcB8wQflFBh5/2lRDS8AzQJpu/x5sQyLWpdOZJN+IQBofM1mSw=="); /*Service Key*/
    URL url = new URL(urlBuilder.toString());
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-type", "application/json");
    System.out.println("Response code: " + conn.getResponseCode());
    BufferedReader rd;
    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
      rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    } else {
      rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    }
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
      sb.append(line);
    }
    rd.close();
    conn.disconnect();
    System.out.println(sb.toString());
    return ResponseDto.success(sb);
  }


  //ticket 삭제 메서드. ticket에 포함된 하위 요소들도 모두 같이 삭제된다.
  @Transactional
  public ResponseDto<?> deleteTicket(Long id, HttpServletRequest request) {

    Member member = isValipassengerAccess(request);
    if (null == member) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    Ticket ticket = isPresentTicket(id);
    if (null == ticket) {
      return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
    }

    //ticket과 연결된 passenger 삭제
    passengerRepository.deleteAllByTicket(ticket);
    //ticket 삭제
    ticketRepository.delete(ticket);

    return ResponseDto.success("delete success");
  }

  //ticket 존재 여부 확인 메서드. 해당 id의 ticket이 존재하면 ticket을 반환하고 없으면 null
  @Transactional(readOnly = true)
  public Ticket isPresentTicket(Long id) {
    Optional<Ticket> optionalTicket = ticketRepository.findById(id);
    return optionalTicket.orElse(null);
  }

  //토큰 유효성 검사하는 메서드. 정상적인 접근(토큰이 들어있으며 유효함)이면 해당 member 반환, 아니면 null
  public Member isValipassengerAccess(HttpServletRequest request){

    //헤더에 Authorization, RefrehToken 값이 없거나 유효하지 않으면 null
    if (null == request.getHeader("RefreshToken") ||
            !tokenProvider.valipassengerToken(request.getHeader("RefreshToken")) ||
            null == request.getHeader("Authorization")) {
      return null;
    }

    // 유효성 검사 후 해당하는 member 반환
    return tokenProvider.getMemberFromAuthentication();
  }

  public ResponseDto <?> getApi() throws IOException {

  StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList"); /*URL*/
    urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=Rbd1ck8kI%2F5Z3493Di78Ls4RU4ojemoBWVtDTUWyC1O8ll3KhKbwZbIOqUtEeEAj4%2B7hv7z6knIbHLBZV03eng%3D%3D"); /*Service Key*/
    urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
    urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
    urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
    urlBuilder.append("&" + URLEncoder.encode("depAirportId", "UTF-8") + "=" + URLEncoder.encode("NAARKJJ", "UTF-8")); /*출발공항ID*/
    urlBuilder.append("&" + URLEncoder.encode("arrAirportId", "UTF-8") + "=" + URLEncoder.encode("NAARKPC", "UTF-8")); /*도착공항ID*/
    urlBuilder.append("&" + URLEncoder.encode("depPlandTime", "UTF-8") + "=" + URLEncoder.encode("20201201", "UTF-8")); /*출발일(YYYYMMDD)*/
    urlBuilder.append("&" + URLEncoder.encode("airlineId", "UTF-8") + "=" + URLEncoder.encode("AAR", "UTF-8")); /*항공사ID*/
  URL url = new URL(urlBuilder.toString());
  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-type", "application/json");
    System.out.println("Response code: " + conn.getResponseCode());
  BufferedReader rd;
    if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
  } else {
    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
  }
    if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
    rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
  } else {
    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
  }
  StringBuilder sb = new StringBuilder();
  String line;
    while ((line = rd.readLine()) != null) {
    sb.append(line);
  }
    rd.close();
    conn.disconnect();
    System.out.println(sb.toString());

    return ResponseDto.success(sb);

}

}
