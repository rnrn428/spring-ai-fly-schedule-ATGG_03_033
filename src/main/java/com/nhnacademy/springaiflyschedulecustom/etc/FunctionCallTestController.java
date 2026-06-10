//package com.nhnacademy.springaiflyschedulecustom.controller;
//
//import com.nhnacademy.springaiflyschedulecustom.service.FunctionCallTestService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/test")
//public class FunctionCallTestController {
//    private final FunctionCallTestService functionCallTestService;
//
//    public FunctionCallTestController(FunctionCallTestService functionCallTestService) {
//        this.functionCallTestService = functionCallTestService;
//    }
//
//    @GetMapping("/function-calling") // api/test/function-calling?message=10과 20의 합은 ?
//    public String testFunctionCalling(@RequestParam("message") String message){
//        return functionCallTestService.testFunctionCalling(message);
//
//    }
//}
