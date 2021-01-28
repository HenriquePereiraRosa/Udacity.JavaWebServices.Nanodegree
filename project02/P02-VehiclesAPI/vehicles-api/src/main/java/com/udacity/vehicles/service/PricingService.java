package com.udacity.vehicles.service;

import com.udacity.vehicles.client.prices.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PricingService {

    @Autowired
    RestTemplate restTemplate;

    public Price getPriceByVehicleId(Long vehicleId) {
        ResponseEntity<Price> exchange = restTemplate.exchange("http://pricing-service/services/price?vehicleId=" + vehicleId,
                HttpMethod.GET,
                null,
                Price.class);
        return exchange.getBody();
    }
}
