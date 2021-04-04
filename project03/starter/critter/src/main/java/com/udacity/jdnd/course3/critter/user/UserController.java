package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.user.service.CustomerDTOHelper;
import com.udacity.jdnd.course3.critter.user.service.EmployeeDTOHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerDTOHelper customerDTOHelper;
    @Autowired
    private EmployeeDTOHelper employeeDTOHelper;


    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerDTOHelper.findAll();
    }

    @GetMapping("/customer/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id){
        return customerDTOHelper.findById(id);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return customerDTOHelper.findCustomerByPetId(petId);
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO customerDTOSvd = customerDTOHelper.save(customerDTO);
        return customerDTOSvd;
    }

    @PutMapping("/customer")
    public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO customerDTOSvd = customerDTOHelper.update(customerDTO);
        return customerDTOSvd;
    }

    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployees(){
        return employeeDTOHelper.findAll();
    }

    @GetMapping("/employee/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id){
        return employeeDTOHelper.findById(id);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeDTOHelper.save(employeeDTO);
    }

    @PutMapping("/employee")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeDTOHelper.update(employeeDTO);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return employeeDTOHelper.findById(employeeId);
    }

    @PutMapping("/employee/{employeeId}")
    public EmployeeDTO setAvailability(@RequestBody Set<DayOfWeek> daysAvailable,
                                @PathVariable long employeeId) {
        return employeeDTOHelper.saveAvailability(employeeId, daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return employeeDTOHelper.getAvailability(employeeRequestDTO);
    }

}
