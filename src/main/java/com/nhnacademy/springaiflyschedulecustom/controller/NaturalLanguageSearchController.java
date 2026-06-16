package com.nhnacademy.springaiflyschedulecustom.controller;

import com.nhnacademy.springaiflyschedulecustom.dto.ApiResponse;
import com.nhnacademy.springaiflyschedulecustom.dto.FlightInfoResponse;
import com.nhnacademy.springaiflyschedulecustom.orchestartor.MultiAgentOrchestrator;
import com.nhnacademy.springaiflyschedulecustom.service.ai.LImAnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class NaturalLanguageSearchController {
    private final LImAnalysisService lImAnalysisService;
    private final MultiAgentOrchestrator orchestrator;

    @GetMapping("/ask")
    public ApiResponse<Map<String, List<FlightInfoResponse>>> askChatbot(
            @RequestParam String message
    ){
        log.info("손님 등장 : {}", message);

        Map<String, Object> params = lImAnalysisService.extractFlightSearchParams(message);

        if(params.isEmpty() || params.get("departure") == null || params.get("arrival") == null || params.get("date") == null){
            log.error("분석 실패 : 출발지, 도착지, 날짜를 정확히 파악하지 못했습니다.");
            return ApiResponse.error("출발지, 도착지, 날짜를 정확히 말씀해 주세요");
        }

        String departure = String.valueOf(params.get("departure"));
        String arrival = String.valueOf(params.get("arrival"));
        String date = String.valueOf(params.get("date"));

        String afterTime = params.get("afterTime") != null ? String.valueOf(params.get("afterTime")) : null;
        Integer minPrice = params.get("minPrice") != null ? Integer.valueOf(String.valueOf(params.get("minPrice"))) : null;
        Integer maxPriceRaw = params.get("maxPrice") != null ? Integer.valueOf(String.valueOf(params.get("maxPrice"))) : null;
        Integer maxPrice = (maxPriceRaw != null && maxPriceRaw > 0) ? maxPriceRaw : null;

        log.info("출발 = {}, 도착 = {}, 날짜 = {}, 시간 = {}, 예산 = {}", departure, arrival, date, afterTime, maxPrice);

        Map<String, List<FlightInfoResponse>> result = orchestrator.coordinateAdvanceSearch(departure, arrival, date, afterTime, minPrice, maxPrice);

        return ApiResponse.success(result);

    }
}
