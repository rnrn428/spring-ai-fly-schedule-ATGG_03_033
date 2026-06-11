package com.nhnacademy.springaiflyschedulecustom.mcptool;

import com.nhnacademy.springaiflyschedulecustom.dto.FlightInfoResponse;
import com.nhnacademy.springaiflyschedulecustom.service.ApiClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlightSearchTool {
    private final ApiClientService apiClientService;

    @Tool(description = """
            출발 공항 코드,  도착 공항 코드, 날짜를 입력받아 실제 항공편 스케줄 목록을 검색합니다.
            
            언제 사용:
                - 사용자가 특정 노선(출발지 -> 도착지)의 비행기 시간표나 비행기 표를 찾아달라고 할 때 사용하세요.
            주의 사항:
                - 공항 이름(예: 광주, 제주)을 직접 넣지 말고, 반드시 AirportInfoTool을 먼저 사용해서 3자리 영문 공항 코드로 변환한 뒤에 이 도구를 호출하세요.
                - 날짜는 반드시 YYYYMMDD 형태의 숫자(예 : 20260611)로 변환해서 넣으세요.
            """)
    public List<FlightInfoResponse> searchFlightByAirline(
            @ToolParam(description = "출발 공항의 3자리 IATA 코드 (예: KWJ, GMP)") String depAirportId,
            @ToolParam(description = "도착 공항의 3자리 IATA 코드 (예: CJU, PUS)") String arrAirportId,
            @ToolParam(description = "조회 날짜 (YYYYMMDD 형식, 예 : 20260611") String date
    ){
        return apiClientService.getFlightSchedule(depAirportId, arrAirportId, date);
    }
}
