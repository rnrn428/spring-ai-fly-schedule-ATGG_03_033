package com.nhnacademy.springaiflyschedulecustom.controller;

import com.nhnacademy.springaiflyschedulecustom.dto.AirlineInfoResponse;
import com.nhnacademy.springaiflyschedulecustom.dto.AirportInfoResponse;
import com.nhnacademy.springaiflyschedulecustom.dto.ApiResponse;
import com.nhnacademy.springaiflyschedulecustom.dto.FlightInfoResponse;
import com.nhnacademy.springaiflyschedulecustom.mcptool.AirlineInfoTool;
import com.nhnacademy.springaiflyschedulecustom.mcptool.AirportInfoTool;
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
    private final AirportInfoTool airportInfoTool;
    private final AirlineInfoTool airlineInfoTool;

    @GetMapping("/flights/search")
    public ApiResponse<List<FlightInfoResponse>> searchFlights(
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam String date
    ){
        List<FlightInfoResponse> result = flightSearchTool.searchFlightByAirline(departure, arrival, date);

        return ApiResponse.success(result);
    }

    // 공항 목록
    @GetMapping("/airports")
    public ApiResponse<List<AirportInfoResponse>> searchAirportList(){
        List<AirportInfoResponse> result = airportInfoTool.getAirportList();
        return ApiResponse.success(result);
    }

    // 항공사 목록
    @GetMapping("/airlines")
    public ApiResponse<List<AirlineInfoResponse>> searchAirlineList(){
        List<AirlineInfoResponse> result = airlineInfoTool.getAirlineList();
        return ApiResponse.success(result);
    }

    // 공항 이름으로 공항 코드 검색
    @GetMapping("/airports/code")
    public ApiResponse<String> searchAirportCode(@RequestParam String airportName){
        String airportCode = airportInfoTool.getAirportCode(airportName);
        return ApiResponse.success(airportCode);
    }
    // 항공사 이름으로 항공사 코드 검색
    @GetMapping("/airlines/code")
    public ApiResponse<String> searchAirlineCode(@RequestParam String airlineName){
        String airlineCode = airlineInfoTool.getAirlineId(airlineName);
        return ApiResponse.success(airlineCode);
    }


}
