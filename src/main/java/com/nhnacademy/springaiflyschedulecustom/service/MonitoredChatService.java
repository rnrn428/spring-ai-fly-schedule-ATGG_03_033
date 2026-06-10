package com.nhnacademy.springaiflyschedulecustom.service;

import com.nhnacademy.springaiflyschedulecustom.config.LoggingCallback;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MonitoredChatService {
    private final ChatClient chatClient;
    private final LoggingCallback loggingCallback;

    public MonitoredChatService(@Qualifier("geminiChatClientBuilder") ChatClient.Builder chatClientBuilder,
                                LoggingCallback loggingCallback) {
        this.chatClient = chatClientBuilder.build();
        this.loggingCallback = loggingCallback;
    }

    public String chatWithMonitoring(String userMessage){
        long startTime = System.currentTimeMillis();

        loggingCallback.beforeRequest(userMessage);

        ChatResponse response = chatClient.prompt()
                .user(userMessage)
                .call()
                .chatResponse();

        long duration = System.currentTimeMillis() - startTime;

        loggingCallback.onResponse(userMessage, response, duration);

        return response.getResult().getOutput().getText();
    }
}
