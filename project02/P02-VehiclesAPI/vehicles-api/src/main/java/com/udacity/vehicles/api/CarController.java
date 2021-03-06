package com.udacity.vehicles.api;


import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.service.CarService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping("/cars")
@ApiResponses(value = {
        @ApiResponse(code=400, message = "Bad request, please follow the API documentation for the proper request format."),
        @ApiResponse(code=401, message = "Due to security constraints, your access request cannot be authorized."),
        @ApiResponse(code=404, message = "Not found, please check if the resource is saved."),
        @ApiResponse(code=500, message = "The server is down. Please make sure that the Location and Pricing microservices are running.")
})

class CarController {

    @Autowired
    private CarService carService;
    @Autowired
    private CarResourceAssembler assembler;

    /**
     * Creates a list to store any vehicles.
     * @return list of vehicles
     */
    @GetMapping
    CollectionModel<EntityModel<Car>> list() {
        List<EntityModel<Car>> resources = carService.list().stream().map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(resources,
                linkTo(methodOn(CarController.class).list()).withSelfRel());
    }

    /**
     * Gets information of a specific car by ID.
     * @param id the id number of the given vehicle
     * @return all information for the requested vehicle
     */
    @GetMapping("/{id}")
    EntityModel<Car> get(@PathVariable Long id) {
        /**
         * Done: Use the `findById` method from the Car Service to get car information.
         * Done: Use the `assembler` on that car and return the resulting output.
         *   Update the first line as part of the above implementing.
         */
        Car car = carService.findById(id);
        return assembler.toModel(car);
    }

    /**
     * Posts information to create a new vehicle in the system.
     * @param car A new vehicle to add to the system.
     * @return response that the new vehicle was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */
    @PostMapping
    ResponseEntity<?> post(@Valid @RequestBody Car car) throws URISyntaxException {
        /**
         * DONE: Use the `save` method from the Car Service to save the input car.
         * DONE: Use the `assembler` on that saved car and return as part of the response.
         *   Update the first line as part of the above implementing.
         */
        Car carDb = carService.save(car);
        EntityModel<Car> model = assembler.toModel(carDb);
        return ResponseEntity.created(new URI(model.getContent().getId().toString())).body(model);
    }

    /**
     * Updates the information of a vehicle in the system.
     * @param id The ID number for which to update vehicle information.
     * @param car The updated information about the related vehicle.
     * @return response that the vehicle was updated in the system
     */
    @PutMapping("/{id}")
    ResponseEntity<?> put(@PathVariable Long id, @Valid @RequestBody Car car) {
        /**
         * DOne: Set the id of the input car object to the `id` input.
         * DONE: Save the car using the `save` method from the Car service
         * DONE: Use the `assembler` on that updated car and return as part of the response.
         *   Update the first line as part of the above implementing.
         */
        Car carDb = carService.findById(id);
        car.setId(id);
        carDb = carService.save(car);
        EntityModel<Car> model = assembler.toModel(carDb);
        return ResponseEntity.ok(model);
    }

    /**
     * Removes a vehicle from the system.
     * @param id The ID number of the vehicle to remove.
     * @return response that the related vehicle is no longer in the system
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        /**
         * Done: Use the Car Service to delete the requested vehicle.
         */
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
