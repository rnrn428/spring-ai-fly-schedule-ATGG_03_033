package com.nhnacademy.springaiflyschedulecustom.agent;

import com.nhnacademy.springaiflyschedulecustom.mcptool.AirportInfoTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AirportCodeAgent {
    private final ChatClient chatClient;

    public AirportCodeAgent(
            @Qualifier("ollamaChatClientBuilder") ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    // 공항 이름을 공항 코드로 변환합니다.
    public String extractAirportCode(String text){
        log.info("[AirportCodeAgent] 공항 코드 추출 시작: {}", text);

        if(text != null && text.trim().startsWith("NAARK")){
            return text.trim();
        }

        String prompt = """
                당신은 사용자의 말에서 '공항 이름'을 찾아내어 고유 코드로 변환하는 전문가 입니다.
                
                [규칙]
                1. 당신이 가진 'getAirportCode'도구를 반드시 사용해서 공항 코드를 알아내세요.
                2. 최종 답변은 오직 7자리 영문 공항 코드(예: NAARKSS, NAARKPC) 하나만 출력하세요.
                3. 마침표, 부연 설명, 기호는 일절 금지합니다.
                4. 도구를 써도 코드를 찾을 수 없다면 "UNKNOWN"이라고 대답하세요.
                """;

        String code = chatClient.prompt()
                .system(prompt)
                .user(text)
                .call()
                .content()
                .toString();

        log.info("[AirportCodeAgent] 공항 코드 추출 완료 : {} -> {}", text, code);
        return code;
    }
}
