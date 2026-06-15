package com.nhnacademy.springaiflyschedulecustom.controller;

import com.nhnacademy.springaiflyschedulecustom.orchestartor.MultiAgentOrchestrator;
import com.nhnacademy.springaiflyschedulecustom.dto.ApiResponse;
import com.nhnacademy.springaiflyschedulecustom.dto.FlightInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coordinator")
@RequiredArgsConstructor
public class CoordinatorTestController {
    private final MultiAgentOrchestrator multiAgentOrchestrator;

    @GetMapping("/search")
    public ApiResponse<Map<String, List<FlightInfoResponse>>> advancedSearch(@RequestParam String departure,
                                                                          @RequestParam String arrival,
                                                                          @RequestParam String date,
                                                                             @RequestParam(required = false) String afterTime,
                                                                             @RequestParam(required = false) Integer minPrice,
                                                                             @RequestParam(required = false) Integer maxPrice){
        Map<String, List<FlightInfoResponse>> result = multiAgentOrchestrator.coordinateAdvanceSearch(departure, arrival, date, afterTime, minPrice, maxPrice);

        return ApiResponse.success(result);
    }
}

