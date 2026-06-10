package com.nhnacademy.springaiflyschedulecustom.monitor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class PerformanceMonitor {

    private final ConcurrentHashMap<String, AtomicInteger> callCounts = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, AtomicLong> totalDurations = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, AtomicLong> minDuration = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, AtomicLong> maxDuration = new ConcurrentHashMap<>();

    // Tool 실행 기록
    public void recordToolCall(String toolName, long duration){
        // 호출 횟수 증가
        callCounts.computeIfAbsent(toolName, k-> new AtomicInteger(0)).incrementAndGet();
        // 총 실행 시간 증가
        totalDurations.computeIfAbsent(toolName, k-> new AtomicLong(0)).incrementAndGet();
        // 최소, 최대 시간 업데이트
        minDuration.computeIfAbsent(toolName, k-> new AtomicLong(Long.MAX_VALUE))
                .updateAndGet(current-> Math.min(current, duration));
        maxDuration.computeIfAbsent(toolName, k-> new AtomicLong(Long.MIN_VALUE))
                .updateAndGet(current -> Math.max(current, duration));
    }

    // 성능 통계 출력
    public void printStatistics(){
        log.info("==================== Tool 성능 통계 ====================");
        callCounts.forEach((toolName, count) -> {
            long total = totalDurations.get(toolName).get();
            long min = minDuration.get(toolName).get();
            long max = maxDuration.get(toolName).get();
            double avg = (double) total / count.get();

            log.info("Tool: {}", toolName);
            log.info("  호출 횟수: {}", count.get());
            log.info("  총 실행 시간: {}ms ({}초)", total, total / 1000.0);
            log.info("  평균 실행 시간: {:.2f}ms", avg);
            log.info("  최소 실행 시간: {}ms", min);
            log.info("  최대 실행 시간: {}ms", max);
            log.info("--------------------------------------------------");
        });
    }

    public void reset(){
        callCounts.clear();
        totalDurations.clear();
        maxDuration.clear();
        minDuration.clear();
        log.info("성능 통계가 초기화되었습니다.");
    }
}
