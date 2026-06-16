package com.nhnacademy.springaiflyschedulecustom.service.ai;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Map;

@Slf4j
@Service
public class LImAnalysisService {
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LImAnalysisService(@Qualifier("geminiChatClientBuilder") ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public Map<String, Object> extractFlightSearchParams(String message){
        log.info("LLM 파라미터 추출 : {}", message);

        String systemPrompt = """
                너는 항공편 검색 파라미터 추출 전문가야.
                
                사용자 메시지에서 다음 파라미터를 추출해서 JSON 형식으로 반환해줘:
                - departure: 출발 공항 이름 (예: "광주", "김포", "제주")
                - arrival: 도착 공항 이름 (예: "제주", "김포", "부산")
                - date: 날짜 (예: "내일", "모레", "2026-03-10")
                - afterTime: "이후" 시간 조건 (예: "14:00", "오후 2시")
                - minPrice: 최소 가격 (숫자만)
                - maxPrice: 최대 가격 (숫자만)
                
                파라미터가 없으면 null로 설정해줘.
                반드시 유효한 JSON만 반환해줘.
                """;
        try{
            String response = chatClient.prompt()
                    .system(systemPrompt)
                    .user("다음 말을 분석해서 JSON으로 줘 : "+ message)
                    .call()
                    .content()
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                            .trim();
            log.info("LLM 추출된 JSON : {}", response);

            return objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});
        }catch (Exception e){
            log.error("LLM 파라미터 추출 실패", e);
            return Map.of();
        }
    }

}
