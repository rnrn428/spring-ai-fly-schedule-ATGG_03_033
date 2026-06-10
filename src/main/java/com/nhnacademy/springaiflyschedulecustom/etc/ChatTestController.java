//package com.nhnacademy.springaiflyschedulecustom.controller;
//
//import com.nhnacademy.springaiflyschedulecustom.service.SimpleChatService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/chat")
//public class ChatTestController {
//    private final SimpleChatService simpleChatService;
//
//    public ChatTestController(SimpleChatService simpleChatService) {
//        this.simpleChatService = simpleChatService;
//    }
//
//    @GetMapping("/ollama")
//    public String askOllama(@RequestParam String question){
//        long startTime = System.currentTimeMillis();
//
//        String response = simpleChatService.askOllama(question);
//
//        long endTime = System.currentTimeMillis();
//
//        return "[Ollama 응답 (" + (endTime - startTime) / 1000.0 + "초" + ")]\n" + response ;
//    }
//
//    @GetMapping("/gemini")
//    public String askGemini(@RequestParam String question){
//        long startTime = System.currentTimeMillis();
//
//        String response = simpleChatService.askGemini(question);
//
//        long endTime = System.currentTimeMillis();
//
//        return "[Gemini 응답 (" + (endTime - startTime) / 1000.0 + "초" + ")]\n" + response ;
//    }
//
//}
