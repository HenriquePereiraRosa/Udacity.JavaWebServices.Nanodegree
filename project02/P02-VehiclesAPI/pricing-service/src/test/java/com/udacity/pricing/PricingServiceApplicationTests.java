package com.udacity.pricing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingServiceApplicationTests {

    @LocalServerPort // (Spring) allow injection of server`s Port
    private Integer port;

    @Test
    public void contextLoads() {
    }

}
