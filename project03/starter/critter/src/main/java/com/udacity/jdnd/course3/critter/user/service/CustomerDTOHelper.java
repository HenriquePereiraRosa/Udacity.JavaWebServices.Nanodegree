package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerDTOHelper {

    @Autowired
    private CustomerService customerService;


    /**
     * Gets All Customer information
     *
     * @return the requested information
     */
    public List<CustomerDTO> findAll() {
        List<CustomerDTO> customersDTO = new ArrayList<>();
        List<Customer> customers = customerService.findAll();
        for(Customer customer : customers)
            customersDTO.add(this.objToDTO(customer));
        return customersDTO;
    }

    /**
     * Gets All Customer information including Pets info
     *
     * @return the requested information
     */
    public List<CustomerDTO> findAllFetchByPets() {
        List<CustomerDTO> customersDTO = new ArrayList<>();
        List<Customer> customers = customerService.findAllFetchByPets();
        for(Customer customer : customers)
            customersDTO.add(this.objToDTO(customer));
        return customersDTO;
    }

    /**
     * Gets Customer information by ID (or throws exception if non-existent)
     *
     * @param id the ID number to gather information on
     * @return the requested information
     */
    public CustomerDTO findById(Long id) {
        CustomerDTO customerDTO =  this
                .objToDTO(customerService.findById(id));
        return customerDTO;
    }

    /**
     * Gets Customer information by ID
     *
     * @param petId the ID number to gather information on
     * @return the requested information
     */
    public CustomerDTO findCustomerByPetId(Long petId) {
        Customer customer = customerService.findCustomerByPetId(petId);
        return this.objToDTO(customer);
    }

    /**
     * Either creates Customer
     *
     * @param customerDTO
     * @return the new stored in the repository
     */
    public CustomerDTO save(CustomerDTO customerDTO) {
        return this.objToDTO(customerService
                .save(this.dtoToObj(customerDTO)));
    }

    /**
     * Updates an customer
     *
     * @param customerDTO A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public CustomerDTO update(CustomerDTO customerDTO) {
        return this.objToDTO(customerService
                .save(this.dtoToObj(customerDTO)));
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        customerService.delete(id);
    }

    /**
     * Converts Obj to DTO
     *
     * @param customer to be converted
     * @return customerDTO
     */
    public CustomerDTO objToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        if (customer.getId() != null)
            customerDTO.setId(customer.getId());
        if (customer.getName() != null && !customer.getName().isEmpty())
            customerDTO.setName(customer.getName());
        if (customer.getNotes() != null && !customer.getNotes().isEmpty())
            customerDTO.setNotes(customer.getNotes());
        if (customer.getPhoneNumber() != null && !customer.getPhoneNumber().isEmpty())
            customerDTO.setPhoneNumber(customer.getPhoneNumber());
        if (customer.getPets() != null && !customer.getPets().isEmpty())
            customerDTO.setPets(customer.getPets());
        return customerDTO;
    }

    /**
     * Converto DTO to Obj
     *
     * @param customerDTO the be converted
     */
    public Customer dtoToObj(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        if (customerDTO.getName() != null && !customerDTO.getName().isEmpty())
            customer.setName(customerDTO.getName());
        if (customerDTO.getNotes() != null && !customerDTO.getNotes().isEmpty())
            customer.setNotes(customerDTO.getNotes());
        if (customerDTO.getPhoneNumber() != null && !customerDTO.getPhoneNumber().isEmpty())
            customer.setPhoneNumber(customerDTO.getPhoneNumber());
        if (customerDTO.getPets() != null && !customerDTO.getPets().isEmpty())
            customer.setPets(customerDTO.getPets());
        return customer;
    }
}
