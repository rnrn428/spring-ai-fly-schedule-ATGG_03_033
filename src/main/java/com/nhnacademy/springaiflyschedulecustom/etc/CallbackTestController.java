//package com.nhnacademy.springaiflyschedulecustom.controller;
//
//import com.nhnacademy.springaiflyschedulecustom.dto.FlightInfoResponse;
//import com.nhnacademy.springaiflyschedulecustom.service.ApiClientService;
//import com.nhnacademy.springaiflyschedulecustom.service.MonitoredChatService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/callback")
//@RequiredArgsConstructor
//public class CallbackTestController {
//    private final MonitoredChatService monitoredChatService;
//    private final ApiClientService apiClientService; // 추가
//
//    @GetMapping("/test")
//    public String testCallback(@RequestParam("message") String message){
//        return monitoredChatService.chatWithMonitoring(message);
//    }
//
//
//}
