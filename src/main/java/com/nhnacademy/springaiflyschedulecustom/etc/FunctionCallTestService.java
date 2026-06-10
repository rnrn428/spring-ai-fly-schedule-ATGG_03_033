//package com.nhnacademy.springaiflyschedulecustom.service;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FunctionCallTestService {
//    private final ChatClient chatClient;
//
//    public FunctionCallTestService(
//            @Qualifier("geminiChatClientBuilder") ChatClient.Builder chatClientBuilder) {
//        this.chatClient = chatClientBuilder.build();
//    }
//
//    public String testFunctionCalling(String userMessage){
//        return chatClient.prompt()
//                .user(userMessage)
//                .call()
//                .content();
//    }
//}
