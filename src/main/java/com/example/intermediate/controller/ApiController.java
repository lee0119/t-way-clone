//package com.example.intermediate.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//@RequiredArgsConstructor
//@RestController
//
//public class ApiController {
//
//
//    @GetMapping("/api")
//    public String callApi() throws IOException {
//        StringBuilder result = new StringBuilder();
//        StringBuffer reult = new StringBuffer();
//
//        String urlStr =" http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList";
//
//
//        URL url = new URL(urlStr);
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        urlConnection.setRequestMethod("GET");
//        BufferedReader br;
//        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
//
//        String returnLine;
//
//        while((returnLine = br.readLine()) != null){
//            result.append(returnLine+"\n\r");
//
//        }
//        urlConnection.disconnect();
//
//        return result.toString();
//
//
//    }
//
//
//
//
//}