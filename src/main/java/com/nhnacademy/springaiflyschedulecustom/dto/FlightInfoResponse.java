package com.nhnacademy.springaiflyschedulecustom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightInfoResponse {
    @JsonProperty("arrAirportNm")
    private String arriveAirportName;
    @JsonProperty("vihicleId")
    private String flightId;
    @JsonProperty("airlineNm")
    private String airlineName;
    @JsonProperty("depPlandTime")
    private String departureTime;
    @JsonProperty("arrPlandTime")
    private String arriveTime;
    @JsonProperty("economyCharge")
    private Integer economyCharge;
    @JsonProperty("prestigeCharge")
    private Integer prestigeCharge;
    @JsonProperty("depAirportNm")
    private String departureAirportName;

}
