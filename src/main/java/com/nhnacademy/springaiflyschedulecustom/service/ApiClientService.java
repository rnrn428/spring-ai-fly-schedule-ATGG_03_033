package com.nhnacademy.springaiflyschedulecustom.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.springaiflyschedulecustom.config.DataGoKrApiProperties;
import com.nhnacademy.springaiflyschedulecustom.dto.ApiResponseWrapper;
import com.nhnacademy.springaiflyschedulecustom.dto.FlightInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiClientService {
    private final DataGoKrApiProperties apiProperties;
    private final RestClient restClient = RestClient.create();

    public List<FlightInfoResponse> getFlightSchedule(String depAirportId, String arrAirportId, String date){
        log.info("비행기 표 검색 : {} -> {} ({})", depAirportId, arrAirportId, date);

        try{
            String url = UriComponentsBuilder.fromUriString(apiProperties.getUrl() + "/GetFlightOpratInfoList")
                    .queryParam("serviceKey", apiProperties.getServiceKey())
                    .queryParam("depAirportId", depAirportId)
                    .queryParam("arrAirportId", arrAirportId)
                    .queryParam("depPlandTime", date)
                    .queryParam("_type", "json")
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();

            log.info("API 호출 주소 : {}", url);

            ApiResponseWrapper response = restClient.get()
                    .uri(URI.create(url))
                    .retrieve()
                    .body(ApiResponseWrapper.class);

            if(response != null && response.getResponse() != null){
                String resultCode = response.getResponse().getHeader().getResultCode();
                if("00".equals(resultCode)){
                    List<FlightInfoResponse> items = response.getResponse().getBody().getItems().getItem();
                    log.info("항공편 {}건 조회 완료", items != null ? items.size() : 0);
                    return items != null ? items : Collections.emptyList();
                }else {
                    log.error("공공 데이터 API 에러 : {} - {}",
                            resultCode,
                            response.getResponse().getHeader().getResultMessage());
                }
            }
            return Collections.emptyList();
        }catch (Exception e){
            log.error("통신 에러", e);
            return Collections.emptyList();
        }
    }




}
