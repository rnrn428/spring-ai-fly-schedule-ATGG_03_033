package com.nhnacademy.springaiflyschedulecustom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

//{
//        "header": {
//        "resultCode": "string",
//        "resultMsg": "string"
//        },


//        "body": {
//        "items": {
//        "item": {
//        "arrAirportNm": "string",
//        "vihicleId": "string",
//        "airlineNm": "string",
//        "depPlandTime": "string",
//        "arrPlandTime": "string",
//        "economyCharge": "string",
//        "prestigeCharge": "string",
//        "depAirportNm": "string"
//        }
//        },
//        "numOfRows": "string",
//        "pageNo": "string",
//        "totalCount": "string"
//        }
//        }



@Data
public class ApiResponseWrapper {

    @JsonProperty("response")
    private Response response;

    @Data
    public static class Response{
        @JsonProperty("header")
        private ResponseHeader header;
        @JsonProperty("body")
        private ResponseBody body;
    }

    @Data
    public static class ResponseHeader{
        @JsonProperty("resultCode")
        private String resultCode;
        @JsonProperty("resultMsg")
        private String resultMessage;
    }

    @Data
    public static class ResponseBody{
        @JsonProperty("items")
        private Items items;

        @JsonProperty("numOfRows")
        private String numOfRows;
        @JsonProperty("pageNo")
        private String pageNo;
        @JsonProperty("totalCount")
        private String totalCount;
    }
    @Data
    public static class Items{
        @JsonProperty("item")
        private List<FlightInfoResponse> item;
    }


}
