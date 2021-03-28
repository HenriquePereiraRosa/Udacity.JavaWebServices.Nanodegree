package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
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
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.findAllFetchByPets();
    }

    @GetMapping("/customer/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id){
        return customerService.findById(id);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return customerService.findCustomerByPetId(petId);
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO customerDTOSvd = customerService.save(customerDTO);
        return customerDTOSvd;
    }

    @PutMapping("/customer")
    public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO customerDTOSvd = customerService.update(customerDTO);
        return customerDTOSvd;
    }

    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployees(){
        return employeeService.findAll();
    }

    @GetMapping("/employee/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id){
        return employeeService.findById(id);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.save(employeeDTO);
    }

    @PutMapping("/employee")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.update(employeeDTO);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return employeeService.findById(employeeId);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable,
                                @PathVariable long employeeId) {
        employeeService.saveAvailability(employeeId, daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return employeeService.getAvailability(employeeRequestDTO);
    }

}
