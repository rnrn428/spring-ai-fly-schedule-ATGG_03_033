//package com.nhnacademy.springaiflyschedulecustom.tool;
//
//import org.springframework.ai.tool.annotation.Tool;
//import org.springframework.ai.tool.annotation.ToolParam;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//
//
//@Component
//public class DateTimerTool {
//    @Tool(description = "상대적 날짜를 실제 날짜(YYYY-MM-DD)로 변환합니다. " +
//    "'내일', '모레', '일주일 뒤' 등을 지원합니다.")
//    public String parseDate(
//            @ToolParam(description = "상대적 날짜 표현 (예: 내일, 모레, 3일 뒤)") String relativeDate
//    ){
//        LocalDate today = LocalDate.now();
//
//        return switch (relativeDate){
//            case "내일" -> today.plusDays(1).toString();
//            case "모레" -> today.plusDays(2).toString();
//            case "글피" -> today.plusDays(3).toString();
//            default -> {
//                if(relativeDate.contains("일 뒤")){
//                    int days = Integer.parseInt(relativeDate.replaceAll("[^0-9]", ""));
//                    yield today.plusDays(days).toString();
//                }
//                yield today.toString();
//            }
//        };
//    }
//}
