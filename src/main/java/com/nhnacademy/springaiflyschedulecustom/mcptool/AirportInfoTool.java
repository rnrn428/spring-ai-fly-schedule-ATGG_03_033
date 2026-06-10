package com.nhnacademy.springaiflyschedulecustom.mcptool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AirportInfoTool {
    private final Map<String, String> airportCodes = Map.of(
            "김포", "GMP",
            "제주", "CJU",
            "광주", "KWJ",
            "부산", "PUS"
    );

    @Tool(description = """
            도시 이름이나 공항 이름을 입력받아 3자리 공항 코드(IATA)를 반환합니다.
            
            언제 사용:
                - 항공편 검색을 위해 도시 이름을 공항 코드로 변환해야 할 때
            반환값:
                - 3자리 영문 공항 코드(예:CJU, GMP, KWJ)
                - 만약 찾을 수 없으면 "UNKNOWN" 반환
            """)
    public String getAirportCode(
            @ToolParam(description = "도시 이름 또는 공항 이름 (예: 광주, 제주, 김포)") String cityName
    ){
        return airportCodes.getOrDefault(cityName, "UNKNOWN");
    }
}
