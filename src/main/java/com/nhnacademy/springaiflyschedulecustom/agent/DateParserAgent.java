package com.nhnacademy.springaiflyschedulecustom.agent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class DateParserAgent {

    private static final DateTimeFormatter API_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public String parseDate(String dateInput){
        log.info("[DateParserAgent] 날짜 파싱 요청 - {}", dateInput);

        if(dateInput == null || dateInput.trim().matches("\\d{8}")){
            return dateInput.trim();
        }

        String normalized = dateInput.trim();

        return switch (normalized) {
            case "오늘" -> LocalDate.now().format(API_FORMATTER);
            case "내일" -> LocalDate.now().plusDays(1).format(API_FORMATTER);
            case "모레", "내일모레" -> LocalDate.now().plusDays(2).format(API_FORMATTER);
            default -> normalized.replace("-", ""); // "2026-06-13" -> "20260613"
        };
    }
}
