package com.example.intermediate.service;

import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Passenger;
import com.example.intermediate.domain.Ticket;
import com.example.intermediate.dto.request.MemberRequestDto;
import com.example.intermediate.dto.request.PassengerRequestDto;
import com.example.intermediate.dto.request.TicketRequestDto;
import com.example.intermediate.dto.response.*;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.PassengerRepository;
import com.example.intermediate.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TokenProvider tokenProvider;
    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;


    //ticket 생성 메서드
    @Transactional
    //객체 새로 만들어서
    public ResponseDto<?> createTicket(TicketRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("RefreshToken")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        //랜덤함수+ 중복제거 숫자 13자리 for문 돌려서 랜덤값 안나오게 넹
        Random random = new Random();
        String bookingNum = (int) (Math.random() * 89991) + 10000 + "";

        //dto에 담긴 정보로 Ticket 생성
        Ticket ticket = Ticket.builder()
                .bookingNum(bookingNum)
                .flyNum(requestDto.getFlyNum())
                .startPoint(requestDto.getStartPoint())
                .endPoint(requestDto.getEndPoint())
                .startTime(requestDto.getStartTime())
                .endTime(requestDto.getEndTime())
                .takeTime(requestDto.getTakeTime())
                .charge(requestDto.getCharge())
                .build();

        ticketRepository.save(ticket);

        // 복습
        List<Passenger> passengertList = new ArrayList<>();
        for (int i = 0; i < requestDto.getPassengerList().size(); i++) {
            PassengerRequestDto passengerRequestDto = requestDto.getPassengerList().get(i);
            Passenger passenger = Passenger.builder()
                    .gender(passengerRequestDto.getGender())
                    .country(passengerRequestDto.getCountry())
                    .name(passengerRequestDto.getName())
                    .birth(passengerRequestDto.getBirth())
                    .email(passengerRequestDto.getEmail())
                    .number(passengerRequestDto.getNumber())
                    .ticket(ticket)
                    .build();

            passengertList.add(passenger);
        }
        passengerRepository.saveAll(passengertList);
        return ResponseDto.success(bookingNum);
    }


    // 입력받은 id의 ticket 상세 정보가 담긴 dto 반환 메서드
    @Transactional(readOnly = true)

    public ResponseDto<?> SearchTicket() throws IOException, ParseException {
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
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());


        rd.close();
        System.out.println(sb);
        conn.disconnect();
        List<AirportResponseDto> responseDtoList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray airportList = (JSONArray) items.get("item");
        for (Object o : airportList) {
            JSONObject airport = (JSONObject) o;
            responseDtoList.add(AirportResponseDto.builder()
                    .endPoint(airport.get("arrAirportNm").toString())
                    .endTime(airport.get("arrPlandTime").toString())
                    .startPoint(airport.get("depAirportNm").toString())
                    .startTime(airport.get("depPlandTime").toString())
                    .charge(airport.get("economyCharge").toString())
                    .flyNum(airport.get("vihicleId").toString())
                    .build());

        }
        return ResponseDto.success(responseDtoList);
    }
    //탑승자 정보 입력
//    @Transactional
//
//    public ResponseDto<?> createPassenger(PassengerRequestDto requestDto, HttpServletRequest request) {
//        Passenger passenger = Passenger.builder()
//
//
//                .gender(requestDto.getGender())
//                .country(requestDto.getCountry())
//                .name(requestDto.getName())
//                .birth(requestDto.getBirth())
//                .email(requestDto.getEmail())
//                .number(requestDto.getNumber())
//                .build();
//
//        passengerRepository.save(passenger);
//        return ResponseDto.success(passenger);
//    }

    //    @Transactional
//    public ResponseDto<?> createPassenger(RequestBody request) {
//    //메소드 공부
//        //dto에 담긴 정보로 Passenger 생성
//        Passenger passenger = Passenger.builder().build();
//        passengerRepository.save(passenger);
//        return ResponseDto.success(passenger);
//    }
//
    //티켓 조회하기
    @Transactional(readOnly = true)
    public ResponseDto<?> getTicket(HttpServletRequest request ){  if (null == request.getHeader("RefreshToken")) {
        return ResponseDto.fail("MEMBER_NOT_FOUND",
                "로그인이 필요합니다.");
    }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }




//    public String getTicket(String bookingNum) throws IOException, ParseException {

        Ticket ticket = ticketRepository.findByBookingNum(bookingNum).orElse(null);
        List<Passenger> passengerList = passengerRepository.findAllByTicket(ticket);


        Ticket ticket = Ticket.builder()
                .bookingNum(bookingNum)
                .flyNum(ticketRepository.getFlyNum())
                .startPoint(ticketRepository.getStartPoint())
                .endPoint(ticketRepository.getEndPoint())
                .startTime(requeticketRepositorystDto.getStartTime())
                .endTime(ticketRepository.getEndTime())
                .takeTime(ticketRepository.getTakeTime())
                .charge(ticketRepository.getCharge())
                .build();
        );
    }
        return ResponseDto.success(ticketResponseDtoList);

        List<PassengerResponseDto> PassengerResponseDto = new ArrayList<>();
        for (int i = 0; i < requestDto.getPassengerList().size(); i++) {
            PassengerRequestDto passengerRequestDto = requestDto.getPassengerList().get(i);
            Passenger passenger = Passenger.builder()
                    .gender(passengerRequestDto.getGender())
                    .country(passengerRequestDto.getCountry())
                    .name(passengerRequestDto.getName())
                    .birth(passengerRequestDto.getBirth())
                    .email(passengerRequestDto.getEmail())
                    .number(passengerRequestDto.getNumber())
                    .ticket(ticket)
                    .build();

            passengerRepository.saveAll(passengertList);
        }
    }




    //ticket 삭제 메서드. ticket에 포함된 하위 요소들도 모두 같이 삭제된다.
//    @Transactional
//    public ResponseDto<?> deleteTicket(Long id, HttpServletRequest request) {
//
//        Member member = isValipassengerAccess(request);
//        if (null == member) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        Ticket ticket = isPresentTicket(id);
//        if (null == ticket) {
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
//        }
//    }

//    @Transactional
//    public PassengerResponseDto<?> createPassenger(PassengerRequestDto requestDto) {
//
//        Passenger passenger = Passenger.builder()  //탑승자 생성
//                .userId(requestDto.getUserId())
//                .gender(requestDto.getGender())
//                .build();
//        passengerRepository.save(passenger);
//        return ResponseDto.success("예약에 성공했습니다");
//    }
//

      //ticket과 연결된 passenger 삭제
//      passengerRepository.deleteAllByTicket(ticket);
//      //ticket 삭제
//      ticketRepository.delete(ticket);
//
//      return ResponseDto.success("delete success");
//    }

    //ticket 존재 여부 확인 메서드. 해당 id의 ticket이 존재하면 ticket을 반환하고 없으면 null


    //토큰 유효성 검사하는 메서드. 정상적인 접근(토큰이 들어있으며 유효함)이면 해당 member 반환, 아니면 null
    public Member isValipassengerAccess(HttpServletRequest request) {

      //헤더에 Authorization, RefrehToken 값이 없거나 유효하지 않으면 null
      if (null == request.getHeader("RefreshToken") ||
              !tokenProvider.valipassengerToken(request.getHeader("RefreshToken")) ||
              null == request.getHeader("Authorization")) {
        return null;
      }

      // 유효성 검사 후 해당하는 member 반환
      return tokenProvider.getMemberFromAuthentication();
    }

  }

