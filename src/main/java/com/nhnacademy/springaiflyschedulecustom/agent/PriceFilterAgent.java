package com.nhnacademy.springaiflyschedulecustom.agent;

import com.nhnacademy.springaiflyschedulecustom.dto.FlightInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PriceFilterAgent {

    // 3만원에서 5만원 사이 항공편 찾아줘
    public List<FlightInfoResponse> filterByPriceRange(
            List<FlightInfoResponse> flights,
            Integer minPrice,
            Integer maxPrice
    ) {
        if (flights == null || flights.isEmpty()) {
            return List.of();
        }

        return flights.stream()
                .filter(flight -> {
                    Integer price = flight.getEconomyCharge();
                    // 가격 정보가 없거나 0원이면 제외
                    if (price == null || price == 0) {
                        return false;
                    }
                    // 최소 가격 필터
                    if (minPrice != null && price < minPrice) {
                        return false;
                    }
                    // 최대 가격 필터
                    return maxPrice == null || price <= maxPrice;

                }).collect(Collectors.toList());
    }


    public FlightInfoResponse findMostCheapest(List<FlightInfoResponse> flights){
        return flights.stream()
                .filter(f-> f.getEconomyCharge() != null && f.getEconomyCharge() > 0)
                .min((f1, f2) -> f1.getEconomyCharge().compareTo(f2.getEconomyCharge()))
                .orElse(null);
    }

    public FlightInfoResponse findMostExpensive(List<FlightInfoResponse> flights){
        return flights.stream()
                .filter(f-> f.getEconomyCharge() != null && f.getEconomyCharge() > 0)
                .max((f1, f2) -> f1.getEconomyCharge().compareTo(f2.getEconomyCharge()))
                .orElse(null);
        }

    public double calculateAveragePrice(List<FlightInfoResponse> flights){
        return flights.stream()
                .filter(f -> f.getEconomyCharge() != null && f.getEconomyCharge() > 0)
                .mapToInt(FlightInfoResponse::getEconomyCharge)
                .average()
                .orElse(0.0);
    }
}












