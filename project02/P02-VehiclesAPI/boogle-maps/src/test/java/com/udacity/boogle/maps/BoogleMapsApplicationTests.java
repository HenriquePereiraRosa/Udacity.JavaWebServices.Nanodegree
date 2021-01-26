package com.udacity.boogle.maps;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoogleMapsApplicationTests {

	@LocalServerPort // (Spring) allow injection od server`s Port
	private Integer port;

	@Test
	public void contextLoads() {
	}

}
