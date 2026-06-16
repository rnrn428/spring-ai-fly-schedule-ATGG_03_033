package com.nhnacademy.springaiflyschedulecustom.service.agent;

import com.nhnacademy.springaiflyschedulecustom.dto.FlightInfoResponse;
import com.nhnacademy.springaiflyschedulecustom.service.ApiClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightSearchAgent {

    private final ApiClientService apiClientService;
    private final AirportCodeAgent airportCodeAgent;
    private final DateParserAgent dateParserAgent;


    public List<FlightInfoResponse> searchFlights(String departure, String arrival, String date){
        log.info("[FlightSearchAgent] 검색  출발 :{}, 도착:{}, 날짜:{}", departure, arrival, date);

        String parsedDate = dateParserAgent.parseDate(date);
        String depCode = airportCodeAgent.extractAirportCode(departure);
        String arrCode = airportCodeAgent.extractAirportCode(arrival);

        if("UNKNOWN".equals(depCode) || "UNKNOWN".equals(arrCode)){
            log.info("공항 코드를 번역할 수 없어서 검색을 중단합니다.");
            return List.of();
        }

        return apiClientService.getFlightSchedule(depCode, arrCode, parsedDate);

    }



}
