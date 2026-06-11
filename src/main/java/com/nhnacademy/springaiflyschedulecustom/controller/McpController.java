package com.nhnacademy.springaiflyschedulecustom.controller;

import com.nhnacademy.springaiflyschedulecustom.dto.ApiResponse;
import com.nhnacademy.springaiflyschedulecustom.dto.FlightInfoResponse;
import com.nhnacademy.springaiflyschedulecustom.mcptool.FlightSearchTool;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mcp")
@RequiredArgsConstructor
public class McpController {
    private final FlightSearchTool flightSearchTool;

    @GetMapping("/flights/search")
    public ApiResponse<List<FlightInfoResponse>> searchFlights(
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam String date
    ){
        List<FlightInfoResponse> result = flightSearchTool.searchFlightByAirline(departure, arrival, date);

        return ApiResponse.success(result);
    }
}
