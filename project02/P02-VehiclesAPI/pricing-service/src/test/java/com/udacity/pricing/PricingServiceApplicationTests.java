package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PricingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingServiceApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(PricingServiceApplicationTests.class);

    @LocalServerPort // (Spring) allow injection of server`s Port
    private Integer port;

    @MockBean
    private PricingService pricingService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void priceTesting() {
        Price price;
        Long vehicleId = 1L;
        try {
            RestTemplate rest = new RestTemplate();
            price = rest.exchange("http://localhost:" + port +
                            "/services/price?vehicleId=" + vehicleId,
                    HttpMethod.GET,
                    null,
                    Price.class).getBody();

            Assertions.assertTrue(price.getCurrency().equals("USD"));
            Assertions.assertTrue(price.getVehicleId().equals(vehicleId));

        } catch (Exception e) {
            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
        }
        ;

    }


}
