//package com.example.intermediate.controller;
//
//
//import com.example.intermediate.dto.response.ResponseDto;
//import com.example.intermediate.service.PassengerService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//public class PassengerController {
//
//    private final PassengerService passengerService;
//
//    //입력 받은 id passenger 조회
//    @RequestMapping(value = "/passenger/{id}", method = RequestMethod.GET)
//    public ResponseDto<?> getPassenger(@PathVariable Long id) {
//        return passengerService.getPassenger(id);
//    }
//
////            @RequestParam(value = "flyNum", required = false) String flyNum,
////            @RequestParam(value = "startPoint", required = false) String startPoint,
////            @RequestParam(value = "endPoint", required = false) String endPoint,
////            @RequestParam(value = "startTime", required = false) String startTime,
////            @RequestParam(value = "takeTime", required = false) String takeTime,
////            @RequestParam(value = "endPoiint", required = false) String endTime,
////            @RequestParam(value = "charge", required = false) int charge){
////
////            return passengerService.getPassenger();
//
//
//
//
//
//    //입력 받은 id의 Ticket 전체 passenger 조회
//    @RequestMapping(value = "/passengers/{id}", method = RequestMethod.GET)
//    public ResponseDto<?> getAllPassenger(@PathVariable Long id) {
//        return passengerService.getAllPassenger(id);
//    }
//}
