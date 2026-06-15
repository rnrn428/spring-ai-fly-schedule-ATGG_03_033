package com.nhnacademy.springaiflyschedulecustom.agent;


import com.nhnacademy.springaiflyschedulecustom.dto.FlightInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class TimeFilterAgent {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");

    public LocalTime parseTime(String timeInput){
        if(timeInput == null || timeInput.isBlank()){
            throw new IllegalArgumentException("시간을 입력해주세요.");
        }

        String normalized = timeInput.trim().toLowerCase();

        if(normalized.contains("오후")){
            String numbersOnly = normalized.replaceAll("[^0-9]", "");
            if(!numbersOnly.isEmpty()){
                int hour = Integer.parseInt(numbersOnly);
                if(hour < 12){
                    hour += 12;
                }
                if(hour >= 24){
                    hour = 12;
                }
                return LocalTime.of(hour, 0);
            }
        }

        if(normalized.contains("오전")){
            String numbersOnly = normalized.replaceAll("[^0-9]", "");
            if(!numbersOnly.isEmpty()){
                int hour = Integer.parseInt(numbersOnly);
                if(hour == 12){
                    hour = 0;
                }
                return LocalTime.of(hour, 0);
            }
        }

        try{
            String cleaned = normalized.replace(":", "");
            return LocalTime.parse(cleaned, TIME_FORMATTER);
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("시간 형식이 올바르지 않습니다. (HH:mm 또는 '오후 2시' 등)");
        }
    }

    public List<FlightInfoResponse> filterAfterTime(List<FlightInfoResponse> flights, LocalTime afterTime){
        if(flights == null || flights.isEmpty()){
            return List.of();
        }
        log.info("[TimeFilterAgent] {} 이후 비행기만 필터링합니다..", afterTime);

        return flights.stream()
                .filter(flight -> {
                    try{
                        LocalTime departureTime = parseDepartureTime(flight.getDepartureTime());
                        return !departureTime.isBefore(afterTime);
                    }catch (Exception e){
                        log.warn("시간 파싱 실패 (해당 표 제외) ; {}", flight.getDepartureTime());
                        return false;
                    }
                }).collect(Collectors.toList());
    }
    private LocalTime parseDepartureTime(String departureTime){
        if(departureTime == null || departureTime.length() < 8 ){
            throw new IllegalArgumentException("잘못된 출발 시간 형식");
        }
        String timePart = departureTime.substring(8, 12);
        return LocalTime.parse(timePart, TIME_FORMATTER);
    }



}
