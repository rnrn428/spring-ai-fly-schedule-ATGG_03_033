package com.nhnacademy.springaiflyschedulecustom.service.agent;

import com.nhnacademy.springaiflyschedulecustom.mcptool.AirportInfoTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AirportCodeAgent {
    
    private final AirportInfoTool airportInfoTool;

    public String extractAirportCode(String text){
        log.info("[AirportCodeAgent] 공항 코드 추출 시작: {}", text);

        if(text != null && text.trim().startsWith("NAARK")){
            return text.trim();
        }

        String code = airportInfoTool.getAirportCode(text);

        log.info("[AirportCodeAgent] 공항 코드 추출 완료 : {} -> {}", text, code);
        return code;
    }
}
