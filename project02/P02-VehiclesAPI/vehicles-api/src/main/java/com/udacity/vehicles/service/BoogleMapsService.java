package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BoogleMapsService {

    @Autowired
    RestTemplate restTemplate;

    public Address getAddressByVehicleId(Double lat, Double lon) {
        ResponseEntity<Address> exchange = restTemplate.exchange(
                "http://boogle-maps-service/maps?lat=" + lat + "&lon=" + lon,
                HttpMethod.GET,
                null,
                Address.class);
        return exchange.getBody();
    }
}
