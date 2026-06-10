package com.nhnacademy.springaiflyschedulecustom.mcptool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AirlineInfoTool {
    private final Map<String, String> airlineNames = Map.of(
            "OZ", "아시아나항공",
            "7C", "제주항공",
            "KE", "대한항공",
            "LJ", "진 에어"
    );

    @Tool(description = """
            2자리 항공사 코드를 입력받아 항공사의 정식 한글 이름을 반환합니다.
            언제 사용:
                - 사용자에게 항공편 검색 결과를 보여줄 떄, 코드를 이름으로 변환하기 위해  
            """)
    public String getAirlineName(
            @ToolParam(description = "2자리 영문/숫자 항공사 코드(예: OZ, 7C)") String code
    ){
        return airlineNames.getOrDefault(code, "할 수 없는 항공사 (" + code + ")");
    }

}
