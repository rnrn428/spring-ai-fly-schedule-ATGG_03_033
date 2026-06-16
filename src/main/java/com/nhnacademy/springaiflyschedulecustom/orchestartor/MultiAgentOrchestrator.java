package com.nhnacademy.springaiflyschedulecustom.orchestartor;

import com.nhnacademy.springaiflyschedulecustom.service.agent.FlightSearchAgent;
import com.nhnacademy.springaiflyschedulecustom.service.agent.GroupingAgent;
import com.nhnacademy.springaiflyschedulecustom.service.agent.PriceFilterAgent;
import com.nhnacademy.springaiflyschedulecustom.service.agent.TimeFilterAgent;
import com.nhnacademy.springaiflyschedulecustom.dto.FlightInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MultiAgentOrchestrator {

    private final FlightSearchAgent flightSearchAgent;
    private final TimeFilterAgent timeFilterAgent;
    private final PriceFilterAgent priceFilterAgent;
    private final GroupingAgent groupingAgent;

    public Map<String, List<FlightInfoResponse>> coordinateBasicSearch(String departure, String arrival, String date){
        log.info("[Orchestrator] 검색 지휘 ({} -> {}, {})", departure, arrival, date);

        List<FlightInfoResponse> rawFlights = flightSearchAgent.searchFlights(departure, arrival, date);

        Map<String, List<FlightInfoResponse>> finalResult = groupingAgent.groupByAirline(rawFlights);

        log.info("[Orchestrator] 기본 검색 지휘 완료");
        return finalResult;
    }

    public  Map<String, List<FlightInfoResponse>> coordinateAdvanceSearch(String departure, String arrival, String date,
                                                                          String afterTime, Integer minPrice, Integer maxPrice){
        log.info("[Orchestrator] 고급 검색 지휘");

        List<FlightInfoResponse> currentFlights = flightSearchAgent.searchFlights(departure, arrival, date);
        log.info("표 {}장", currentFlights.size());

        if(afterTime != null && !afterTime.isBlank()){
            LocalTime filterTime = timeFilterAgent.parseTime(afterTime);
            currentFlights = timeFilterAgent.filterAfterTime(currentFlights, filterTime);
            log.info("시간 필터 : {}이후 표 {}장 생존", filterTime, currentFlights.size());
        }

        if(minPrice != null || maxPrice != null){
            currentFlights = priceFilterAgent.filterByPriceRange(currentFlights, minPrice, maxPrice);
            log.info("가격 필터 : {}원 ~ {}원 사이 표 {} 장 생존", minPrice, maxPrice, currentFlights.size());
        }

        Map<String, List<FlightInfoResponse>> finalResult = groupingAgent.groupByAirline(currentFlights);
        log.info("그룹핑 완료 : 총 {}개 항공사로 분류됨", finalResult.size());

        log.info("[Orchestrator] 고급 검색 지휘 완료");
        return finalResult;
    }



}
