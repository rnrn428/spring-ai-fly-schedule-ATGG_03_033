package com.nhnacademy.springaiflyschedulecustom.mcptool;

import com.nhnacademy.springaiflyschedulecustom.dto.AirlineInfoResponse;
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
@RequiredArgsConstructor
@Component
public class AirlineInfoTool {

    private final ApiClientService apiClientService;

    private Map<String, String> getAirlineIdCache = new HashMap<>();

    // 전체 항공사 목록 조회 (내부 캐싱용)
    public List<AirlineInfoResponse> getAirlineList(){
        log.info("MCP Tool 호출 : getAirlineList()");

        List<AirlineInfoResponse> airlines = apiClientService.getAirlineList();

        // 💡 수정: 변수에 할당해야 저장됩니다!
        getAirlineIdCache = airlines.stream().collect(Collectors.toMap(
                AirlineInfoResponse::getAirlineName,
                AirlineInfoResponse::getAirlineId,
                (existing, replacement) -> existing
        ));
        return airlines;
    }

    // 항공사 이름으로 항공사 ID 조회
    @Tool(description = """
            항공사 이름으로 항공사 ID를 조회합니다.
            항공사 이름을 입력하면 해당 항공사의 IATA 코드를 반환합니다.
            범위는 국내 모든 항공사입니다.
            """)
    public String getAirlineId(
            @ToolParam(description = "항공사 이름 입력(예: 아시아나 항공, 대한항공, 제주항공 등)") String airlineName
    )
    {
        log.info("MCP Tool 호출 : getAirlineId(airlineName={})", airlineName);

        if(getAirlineIdCache.isEmpty()){
            getAirlineList();
        }

        String id = getAirlineIdCache.get(airlineName);
        if(id == null){
            log.warn("항공사 ID를 찾을 수 없음 : {}", airlineName);
            return "알 수 없는 항공사입니다ㅣ " + airlineName;
        }
        log.info("항공사 ID 조회 결과 : {} -> {}", airlineName, id);
        return id;

    }

}
