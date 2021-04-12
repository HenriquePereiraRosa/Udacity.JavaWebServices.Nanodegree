package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.exception.custom.CouldNotBeNullException;
import com.udacity.jdnd.course3.critter.exception.custom.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.user.*;
import com.udacity.jdnd.course3.critter.user.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;


    /**
     * Gets All Customer information
     *
     * @return the requested information
     */
    public List<Customer> findAll() {
        List<Customer> customers = customerRepo.findAll();
        return customers;
    }

    /**
     * Gets All Customer information including Pets info
     *
     * @return the requested information
     */
    public List<Customer> findAllFetchByPets() {
        List<Customer> customers = customerRepo.findAllFetchPets();
        return customers;
    }

    /**
     * Gets Customer information by ID (or throws exception if non-existent)
     *
     * @param id the ID number to gather information on
     * @return the requested information
     */
    public Customer findById(Long id) {
        Optional<Customer> obj = customerRepo.findById(id);
        if (obj.isEmpty())
            throw new ResourceNotFoundException();

        return obj.get();
    }

    /**
     * Gets Customer information by Pet ID
     *
     * @param petId the ID number to gather information on
     * @return the requested information
     */
    public Customer findCustomerByPetId(Long petId) {
        Customer customer = customerRepo.findCustomerByPetId(petId).get(0);
        return customer;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     *
     * @param customer
     *A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Customer save(Customer customer) {
        if (customer.getName() == null)
            throw new CouldNotBeNullException();

        return customerRepo.save(customer);
    }

    /**
     * Updates an customer
     *
     * @param customer
     *A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Customer update(Customer customer) {
        return customerRepo.findOneByName(customer.getName())
                .map(objToBeUpdated -> {
                    if (customer.getName() != null)
                        objToBeUpdated.setName(customer.getName());
                    if (customer.getPhoneNumber() != null)
                        objToBeUpdated.setPhoneNumber(customer.getPhoneNumber());
                    if (customer.getNotes() != null)
                        objToBeUpdated.setNotes(customer.getNotes());
                    if (customer.getPets() != null)
                        objToBeUpdated.setPets(customer.getPets());
                    return customerRepo.save(objToBeUpdated);
                }).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Deletes a given car by ID (or throws exception if non-existent)
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Optional<Customer> obj = customerRepo.findById(id);
        if (obj.isEmpty())
            throw new ResourceNotFoundException();
        customerRepo.delete(obj.get());
    }
}
