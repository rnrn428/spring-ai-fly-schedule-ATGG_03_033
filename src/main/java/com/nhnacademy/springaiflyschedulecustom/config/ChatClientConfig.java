package com.nhnacademy.springaiflyschedulecustom.config;

import com.nhnacademy.springaiflyschedulecustom.mcptool.AirlineInfoTool;
import com.nhnacademy.springaiflyschedulecustom.mcptool.AirportInfoTool;
import com.nhnacademy.springaiflyschedulecustom.mcptool.FlightSearchTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class ChatClientConfig {
    @Bean
    public ChatClient.Builder ollamaChatClientBuilder(
            @Qualifier("ollamaChatModel")ChatModel ollamaChatModel,
            AirlineInfoTool airlineInfoTool,
            AirportInfoTool airportInfoTool,
            FlightSearchTool flightSearchTool
            ){
        return ChatClient.builder(ollamaChatModel)
                .defaultTools(airportInfoTool, airlineInfoTool, flightSearchTool)
                .defaultAdvisors(new SimpleLoggerAdvisor());
    }

    @Bean
    @Primary
    public ChatClient.Builder geminiChatClientBuilder(
            @Qualifier("googleGenAiChatModel") ChatModel geminiChatModel,
            AirportInfoTool airportInfoTool,
            AirlineInfoTool airlineInfoTool,
            FlightSearchTool flightSearchTool
    ){
        return ChatClient.builder(geminiChatModel)
                .defaultTools( airportInfoTool, airlineInfoTool, flightSearchTool)
                .defaultAdvisors(new SimpleLoggerAdvisor());
    }
}
