package com.nhnacademy.springaiflyschedulecustom.agent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class DateParserAgent {
    private final ChatClient chatClient;

    public DateParserAgent(
            @Qualifier("ollamaChatClientBuilder") ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String parseDate(String dateInput){
        log.info("DateParserAgent: 날짜 파싱 요청 - {}", dateInput);

        if(dateInput == null || dateInput.trim().matches("\\d{8}")){
            return dateInput.trim();
        }

        log.info("[DateParserAgent] 자연어 날짜 번역 : {}", dateInput);

        String today = LocalDate.now().toString();

        String prompt = """
                당신은 사용자의 자연어 날짜 표현을 'YYYYMMDD'형태의 8자리 숫자로 변환하는 AI입니다.
                오늘 날짜는 %s입니다. 이 날짜를 기준으로 계산하세요.
                
                [절대 규칙]
                1. 오직 8자리 숫자로 구성된 문자열 하나만 대답하세요. (마침표, 쉼표, 설명 절대 금지)
                2. 연도가 명확하지 않으면 기본적으로 올해로 간주하세요.
                3. 사용자의 의도를 전혀 파악할 수 없다면 "UNKNOWN"이라고 대답하세요.
                
                [예시]
                - 내일 -> 20260613 (오늘이 2026-06-12일 경우)
                - 다음주 수요일 -> 20260617
                - 12월 25일 -> 20261225
                """.formatted(today);

        String parsedDate = chatClient.prompt()
                .system(prompt)
                .user(dateInput)
                .call()
                .content()
                .trim();

        log.info("[DateParserAgent] 번역 완료 : {} -> {}", dateInput, parsedDate);

        return parsedDate;
    }



}
