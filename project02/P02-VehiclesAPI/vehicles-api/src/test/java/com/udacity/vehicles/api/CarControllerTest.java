package com.udacity.vehicles.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.Price;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.service.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Implements testing of the CarController class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerTest {

    private static final Logger log = LoggerFactory.getLogger(CarControllerTest.class);

    @LocalServerPort // (Spring) allow injection of server`s Port
    private Integer port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Car> json;

    @MockBean
    private CarService carService;

    @MockBean
    private PriceClient priceClient;

    @MockBean
    private MapsClient mapsClient;

    /**
     * Creates pre-requisites for testing, such as an example car.
     */
    @BeforeEach
    public void setup() {
        Car car = getCar();
        car.setId(1L);
        given(carService.save(any())).willReturn(car);
        given(carService.findById(any())).willReturn(car);
        given(carService.list()).willReturn(Collections.singletonList(car));
    }

    /**
     * Tests for successful creation of new car in the system
     *
     * @throws Exception when car creation fails in the system
     */
    @Test
    public void createCar() throws Exception {
        Car car = getCar();
        mvc.perform(
                post(new URI("/cars"))
                        .content(json.write(car).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    /**
     * Tests if the read operation appropriately returns a list of vehicles.
     *
     * @throws Exception if the read operation of the vehicle list fails
     */
    @Test
    public void listCars() throws Exception {
        /**
         * Done: Add a test to check that the `get` method works by calling
         *   the whole list of vehicles. This should utilize the car from `getCar()`
         *   below (the vehicle will be the first in the list).
         */
        String url = new StringBuilder("http://localhost:"
                + port + "/cars")
                .toString();

        Car car01 = this.getCar();

        List<Car> cars = new ArrayList<>();
        cars.add(car01);

        for (Car item : cars) {

            RestTemplate rest = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> requestEntity = new HttpEntity<Object>(item, headers);

            ResponseEntity<Car> entity = rest.exchange(url,
                    HttpMethod.POST,
                    requestEntity,
                    Car.class);
            cars.set(cars.indexOf(item), entity.getBody());
        }

        ParameterizedTypeReference ref = new ParameterizedTypeReference<List<Car>>() {
        };

        RestTemplate rest = new RestTemplate();

        ResponseEntity<Map> res =
                rest.exchange(url,
                        HttpMethod.GET,
                        null,
                        Map.class);
        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
        Map carsMap = (Map) res.getBody().get("_embedded");
        List<Map> resCars = (List) carsMap.get("carList");

        Assertions.assertEquals(cars.size(), resCars.size());

        for (Map item : resCars) {
            Assertions.assertEquals(1, item.get("id"));
            Assertions.assertEquals("USED", item.get("condition"));
        }

    }

    /**
     * Tests the read operation for a single car by ID.
     *
     * @throws Exception if the read operation for a single car fails
     */
    @Test
    public void findCar() throws Exception {
        /**
         * Done: Add a test to check that the `get` method works by calling
         *   a vehicle by ID. This should utilize the car from `getCar()` below.
         */
        String url = new StringBuilder("http://localhost:"
                + port + "/cars/")
                .toString();

        Car car01 = this.getCar();

        List<Car> cars = new ArrayList<>();
        cars.add(car01);

        for (Car item : cars) {

            RestTemplate rest = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> requestEntity = new HttpEntity<Object>(item, headers);

            ResponseEntity<Car> entity = rest.exchange(url,
                    HttpMethod.POST,
                    requestEntity,
                    Car.class);
            cars.set(cars.indexOf(item), entity.getBody());
        }

        ParameterizedTypeReference ref = new ParameterizedTypeReference<List<Car>>() {
        };

        RestTemplate rest = new RestTemplate();


        for (Car item : cars) {
            ResponseEntity<Map> res =
                    rest.exchange(url + item.getId(),
                            HttpMethod.GET,
                            null,
                            Map.class);
            Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());

            Map carsMap = (Map) res.getBody();
            Assertions.assertEquals(1, carsMap.get("id"));
            Assertions.assertEquals("USED", carsMap.get("condition"));
        }
    }

    /**
     * Tests the read operation for a single car by ID.
     *
     * @throws Exception if the read operation for a single car fails
     */
    @Test
    public void updateCar() throws Exception {
        /**
         * Done: Add a test to check that the `get` method works by calling
         *   a vehicle by ID. This should utilize the car from `getCar()` below.
         */
        String url = new StringBuilder("http://localhost:"
                + port + "/cars/")
                .toString();

        RestTemplate rest = new RestTemplate();

        Car car01 = this.getCar();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity<Object>(car01, headers);

        ResponseEntity<Car> res01 = rest.exchange(url,
                HttpMethod.POST,
                requestEntity,
                Car.class);
        car01 = res01.getBody();

        ParameterizedTypeReference ref = new ParameterizedTypeReference<List<Car>>() {
        };

        Location newLocation = new Location(60.73061, -53.935242);
        Price newPrice = new Price();
        newPrice.setPrice(new BigDecimal(99.000));
        newPrice.setCurrency("BRL");
        newPrice.setVehicleId(car01.getId());

        car01.setCondition(Condition.NEW);
        car01.setLocation(newLocation);
        car01.setPrice(newPrice);

        requestEntity = new HttpEntity<Object>(car01, headers);
        ResponseEntity<Car> res02 =
                rest.exchange(url + car01.getId(),
                        HttpMethod.PUT,
                        requestEntity,
                        Car.class);
        Assertions.assertEquals(HttpStatus.OK, res02.getStatusCode());
    }

    /**
     * Tests the deletion of a single car by ID.
     *
     * @throws Exception if the delete operation of a vehicle fails
     */
    @Test
    public void deleteCar() throws Exception {
        /**
         * Done: Add a test to check whether a vehicle is appropriately deleted
         *   when the `delete` method is called from the Car Controller. This
         *   should utilize the car from `getCar()` below.
         */
        String url = new StringBuilder("http://localhost:"
                + port + "/cars/")
                .toString();

        Car car01 = this.getCar();

        List<Car> cars = new ArrayList<>();
        cars.add(car01);

        for (Car item : cars) {

            RestTemplate rest = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> requestEntity = new HttpEntity<Object>(item, headers);

            ResponseEntity<Car> entity = rest.exchange(url,
                    HttpMethod.POST,
                    requestEntity,
                    Car.class);
            cars.set(cars.indexOf(item), entity.getBody());
        }

        ParameterizedTypeReference ref = new ParameterizedTypeReference<List<Car>>() {
        };

        RestTemplate rest = new RestTemplate();


        for (Car item : cars) {
            ResponseEntity<Map> res =
                    rest.exchange(url + item.getId(),
                            HttpMethod.DELETE,
                            null,
                            Map.class);
            Assertions.assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());
        }
    }

    /**
     * Creates an example Car object for use in testing.
     *
     * @return an example Car object
     */
    private Car getCar() {
        Car car = new Car();
        car.setLocation(new Location(40.730610, -73.935242));
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);
        details.setModel("Impala");
        details.setMileage(32280);
        details.setExternalColor("white");
        details.setBody("sedan");
        details.setEngine("3.6L V6");
        details.setFuelType("Gasoline");
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }
}