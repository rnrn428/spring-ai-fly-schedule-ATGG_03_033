package com.nhnacademy.springaiflyschedulecustom.agent;


import com.nhnacademy.springaiflyschedulecustom.dto.FlightInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GroupingAgent {
    public Map<String, List<FlightInfoResponse>> groupByAirline(List<FlightInfoResponse> flights){
        log.info("[GroupAgent] 항공사별 그룹핑 총 {}편", flights.size());

        Map<String, List<FlightInfoResponse>> grouped = flights.stream()
                .collect(Collectors.groupingBy(FlightInfoResponse::getAirlineName));

        log.info("[GroupAgent] 총 {}개 항공사", grouped.size());
        return grouped;
    }
}
