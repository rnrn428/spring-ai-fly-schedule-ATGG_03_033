package com.nhnacademy.springaiflyschedulecustom.mcptool;

import com.nhnacademy.springaiflyschedulecustom.dto.AirportInfoResponse;
import com.nhnacademy.springaiflyschedulecustom.service.ApiClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AirportInfoTool {
    private final ApiClientService apiClientService;

    private Map<String, String> airportCodeCache = new HashMap<>();

    // 전체 공항 목록 조회 (AI용 도구가 아닌 내부 캐싱용 메서드)
    public List<AirportInfoResponse> getAirportList(){
        log.info("MCP Tool 호출 : getAirportList()");

        List<AirportInfoResponse> airports = apiClientService.getAirportList();

        airportCodeCache = airports.stream().collect(Collectors.toMap(
                AirportInfoResponse::getAirportName,
                AirportInfoResponse::getAirportId,
                (existing, replacement) -> existing
        ));
        return airports;
    }

    // 공항 이름으로 공항 코드 조회
    @Tool(description = """
            공항 이름으로 공항 코드를 조회합니다.
            공항 이름을 입력하면 공합 코드의 IATA 코드를 반환합니다.
            국내에 있는 모든 공항이 범위입니다.
            """)
    public String getAirportCode(
            @ToolParam(description = "공함 이름 입력(예: 제주, 김포, 광주 등)") String airportName
    ){
        log.info("getAirportCode(airportName={})", airportName);

        if(airportCodeCache.isEmpty()){
            getAirportList();
        }
        String code = airportCodeCache.get(airportName);

        if(code == null){
            log.warn("공항 코드를 찾을 수 없음 : {}", airportName);
            return "알 수 없는 공항입니다: " + airportName;
        }
        log.info("공항 코드 조회 결과: {} -> {}", airportName, code);
        return code;
    }



}
