//package com.nhnacademy.springaiflyschedulecustom.tool;
//
//import org.springframework.ai.tool.annotation.Tool;
//import org.springframework.ai.tool.annotation.ToolParam;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CalculatorTool {
//
//    @Tool(description = "두 숫자의 합을 계산합니다.")
//    public int add(
//            @ToolParam(description = "첫 번째 숫자") int a,
//            @ToolParam(description = "두 번째 숫자") int b
//    ){
//        return a + b;
//    }
//
//    @Tool(description = "두 숫자의 곱을 계산합니다.")
//    public int multiply(
//            @ToolParam(description = "첫 번째 숫자") int a,
//            @ToolParam(description = "두 번째 숫자") int b
//    ){
//        return a * b;
//    }
//}
