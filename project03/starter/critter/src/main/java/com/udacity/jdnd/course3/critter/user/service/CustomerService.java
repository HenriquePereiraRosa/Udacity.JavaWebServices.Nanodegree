package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.exception.NotFoundException;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    /**
     * Gathers a list of all vehicles
     *
     * @return a list of all vehicles in the CarRepository
     */
    public List<Customer> list() {
        return customerRepo.findAll();
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
            throw new NotFoundException("Customer Not Found.");

        return obj.get();
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     *
     * @param customerDTO A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public CustomerDTO save(CustomerDTO customerDTO) {
//        if (customerDTO.getName() != null) {
//            return this.update(customerDTO);
//        }
        return this.objToDTO(customerRepo.save(this.dtoToObj(customerDTO)));
    }

    /**
     * Updates an customer
     *
     * @param customerDTO A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public CustomerDTO update(CustomerDTO customerDTO) {
        if (customerDTO.getName() == null)
            throw new NotFoundException();

        return this.objToDTO(customerRepo.findByName(customerDTO.getName())
                .map(objToBeUpdated -> {
                    if (customerDTO.getName() != null)
                        objToBeUpdated.setName(customerDTO.getName());
                    if (customerDTO.getPhoneNumber() != null)
                        objToBeUpdated.setPhoneNumber(customerDTO.getPhoneNumber());
                    if (customerDTO.getNotes() != null)
                        objToBeUpdated.setNotes(customerDTO.getNotes());
                    if (customerDTO.getPets() != null)
                        objToBeUpdated.setPets(customerDTO.getPets());
                    return customerRepo.save(objToBeUpdated);
                }).orElseThrow(NotFoundException::new));
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Optional<Customer> obj = customerRepo.findById(id);
        if (obj.isEmpty())
            throw new NotFoundException("Customer Not Found.");

        customerRepo.delete(obj.get());
    }

    /**
     * Converts Obj to DTO
     *
     * @param customer to be converted
     * @return  customerDTO
     */
    public CustomerDTO objToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
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
        customer.setName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setPets(customerDTO.getPets());
        return customer;
    }

    public List<CustomerDTO> findAll() {
        List<CustomerDTO> customersDTO = new ArrayList<>();
        List<Customer> customers = customerRepo.findAll();
        customers.forEach(customer -> customersDTO.add(this.objToDTO(customer)));
        return customersDTO;
    }

    public List<CustomerDTO> findAllFetchByPets() {
        List<CustomerDTO> customersDTO = new ArrayList<>();
        List<Customer> customers = customerRepo.findAllFetchByPets();
        customers.forEach(customer -> customersDTO.add(this.objToDTO(customer)));
        return customersDTO;
    }
}
