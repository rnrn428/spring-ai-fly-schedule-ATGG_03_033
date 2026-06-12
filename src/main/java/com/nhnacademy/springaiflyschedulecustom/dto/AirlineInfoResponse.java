package com.nhnacademy.springaiflyschedulecustom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirlineInfoResponse {
    @JsonProperty("airlineId")
    private String airlineId;
    @JsonProperty("airlineNm")
    private String airlineName;
}
