package com.udacity.vehicles.service;

import com.netflix.discovery.converters.Auto;
import com.udacity.vehicles.client.maps.Address;
import com.udacity.vehicles.client.prices.Price;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    @Autowired
    private CarRepository repository;
    @Autowired
    private PricingService pricingService;
    @Autowired
    private BoogleMapsService boogleMapsService;

//    Another Example of BEAN Injection:
//    private final CarRepository repository;
//    @Autowired
//    public void setDogService(PricingService pricingService) {
//        this.pricingService = pricingService;
//    }

    public CarService(CarRepository repository) {
        /**
         * Done: Add the Maps and Pricing Web Clients you create
         *   in `VehiclesApiApplication` as arguments and set them here.
         */
        this.repository = repository;
    }

    /**
     * Gathers a list of all vehicles
     *
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     *
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        /**
         * Done: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         *   Remove the below code as part of your implementation.
         */
        Optional<Car> car = repository.findById(id);
        if (car.isEmpty())
            throw new CarNotFoundException("Car Not Found.");

        /**
         * Done: Use the Pricing Web client you create in `VehiclesApiApplication`
         *   to get the price based on the `id` input'
         * Done: Set the price of the car
         * Note: The car class file uses @transient, meaning you will need to call
         *   the pricing service each time to get the price.
         */
        Price price = pricingService.getPriceByVehicleId(car.get().getId());
        car.get().setPrice(price);

        /**
         * Done: Use the Maps Web client you create in `VehiclesApiApplication`
         *   to get the address for the vehicle. You should access the location
         *   from the car object and feed it to the Maps service.
         * Done: Set the location of the vehicle, including the address information
         * Note: The Location class file also uses @transient for the address,
         * meaning the Maps service needs to be called each time for the address.
         */
        car.get().getLocation().setAddress(generateAddress(car.get()));

        return car.get();
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     *
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        if (car.getDetails() != null)
                            carToBeUpdated.setDetails(car.getDetails());
                        if (car.getCondition() != null)
                            carToBeUpdated.setCondition(car.getCondition());
                        if (car.getPrice() != null)
                            carToBeUpdated.setPrice(car.getPrice());
                        if (car.getLocation() != null)
                            carToBeUpdated.setLocation(car.getLocation());
                        carToBeUpdated.setModifiedAt(LocalDateTime.now());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }
        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        /**
         * Done: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         */
        Optional<Car> car = repository.findById(id);
        if (car.isEmpty())
            throw new CarNotFoundException("Car Not Found.");

        /**
         * Done: Delete the car from the repository.
         */
        repository.delete(car.get());


    }

    private Address generateAddress(Car car) {
        Double lat = car.getLocation().getLat();
        Double lon = car.getLocation().getLon();
        Address address = boogleMapsService.getAddressByVehicleId(lat, lon);
        return address;
    }
}
