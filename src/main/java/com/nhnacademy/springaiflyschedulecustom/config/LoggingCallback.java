package com.nhnacademy.springaiflyschedulecustom.config;

import com.nhnacademy.springaiflyschedulecustom.monitor.PerformanceMonitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingCallback {

    private final Map<String, Long> toolExecutionTimes = new ConcurrentHashMap<>();

    private final PerformanceMonitor performanceMonitor;

    public void beforeToolCall(String toolName, Map<String, Object> arguments){
        log.info("========================================");
        log.info(" Tool 호출 시작");
        log.info("Tool 이름: {}", toolName);
        log.info("파라미터: {}", arguments);
        log.info("========================================");

        toolExecutionTimes.put(toolName, System.currentTimeMillis());
    }

    public void beforeRequest(String request){
        log.info("========================================");
        log.info(" LLM 요청 시작");
        log.info("사용자 입력: {}", request);
        log.info("AI 답변을 기다리는 중...");
        log.info("========================================");
    }

    public void afterToolCall(String toolName, Object result){
        Long startTime = toolExecutionTimes.get(toolName);
        long duration = startTime != null ? System.currentTimeMillis() - startTime : 0;

        performanceMonitor.recordToolCall(toolName, duration);

        log.info("========================================");
        log.info(" Tool 호출 완료");
        log.info("Tool 이름: {}", toolName);
        log.info("실행 시간: {}ms", duration);
        log.info("결과 타입: {}", result != null ? result.getClass().getSimpleName() : "null");

        if(result != null){
            String resultStr = result.toString();
            if(resultStr.length() > 500){
                log.info("결과 (요약) : {}... (총 {} 글자)", resultStr.substring(0, 500), resultStr.length());
            }else {
                log.info("결과: {}", resultStr);
            }
        }
        log.info("========================================");

        toolExecutionTimes.remove(toolName);
    }
    public void onResponse(String request, ChatResponse response, long duration){
        log.info("========================================");
        log.info(" LLM 응답 생성 완료");
        log.info("요청: {}", request);
        log.info("전체 실행 시간: {}ms ({}초)", duration, duration / 1000.0);

        if(response != null && response.getMetadata() != null){
            ChatResponseMetadata metadata = response.getMetadata();
            log.info("모델: {}", metadata.getModel());
            log.info("토큰 사용량 : {}", metadata.getUsage());
        }

        if(response != null && !response.getResults().isEmpty()){
            Generation generation = response.getResults().get(0);
            String content = generation.getOutput().getText();
            log.info("응답: {}", content);
        }
        log.info("========================================");
    }
}
