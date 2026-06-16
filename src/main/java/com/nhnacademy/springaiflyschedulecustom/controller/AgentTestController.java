package com.nhnacademy.springaiflyschedulecustom.controller;

import com.nhnacademy.springaiflyschedulecustom.service.agent.AirportCodeAgent;
import com.nhnacademy.springaiflyschedulecustom.service.agent.DateParserAgent;
import com.nhnacademy.springaiflyschedulecustom.service.agent.FlightSearchAgent;
import com.nhnacademy.springaiflyschedulecustom.dto.ApiResponse;
import com.nhnacademy.springaiflyschedulecustom.dto.FlightInfoResponse;
import com.nhnacademy.springaiflyschedulecustom.service.ApiClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentTestController {
    private final DateParserAgent dateParserAgent;
    private final AirportCodeAgent airportCodeAgent;
    private final FlightSearchAgent flightSearchAgent;
    private final ApiClientService apiClientService;

    @GetMapping("/date")
    public ApiResponse<String> parseDate(@RequestParam String text){
        String result = dateParserAgent.parseDate(text);
        return ApiResponse.success(result);
    }

    @GetMapping("/airport")
    public ApiResponse<String> callAirportCode(@RequestParam String text){
        String result = airportCodeAgent.extractAirportCode(text);
        return ApiResponse.success(result);
    }

    @GetMapping("/search")
    public ApiResponse<List<FlightInfoResponse>> testAgentChaining(@RequestParam String departure,
                                                                              @RequestParam String arrival,
                                                                              @RequestParam String date){

            List<FlightInfoResponse> result = flightSearchAgent.searchFlights(departure, arrival, date);

            return ApiResponse.success(result);
    }
}
