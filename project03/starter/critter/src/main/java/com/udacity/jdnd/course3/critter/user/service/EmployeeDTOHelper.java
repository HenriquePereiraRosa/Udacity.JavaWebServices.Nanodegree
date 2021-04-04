package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.exception.custom.CouldNotBeNullException;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeDTOHelper {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Gathers a list of all vehicles
     *
     * @return a list of all vehicles in the CarRepository
     */
    public List<EmployeeDTO> findAll() {
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        List<Employee> employees = employeeService.findAll();
        employees.forEach(employee -> employeeDTOs.add(this.objToDTO(employee)));
        return employeeDTOs;
    }

    /**
     * Gets Customer information by ID
     *
     * @param id the ID number to gather information on
     * @return the requested information
     */
    public EmployeeDTO findById(Long id){
        return this.objToDTO(employeeService.findById(id));
    }

    /**
     * Gets Customer information by ID
     *
     * @param employeeIds the ID number to gather information on
     * @return the requested information
     */
    public List<EmployeeDTO> findAllByIds(List<Long> employeeIds) {
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        List<Employee> employees = employeeService.findAllByIds(employeeIds);
        employees.forEach(employee -> employeeDTOs.add(this.objToDTO(employee)));
        return employeeDTOs;
    }

    /**
     * Creates an Employee
     *
     * @param employeeDTO A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        if (employeeDTO.getName() == null)
            throw new CouldNotBeNullException();
//        if (employeeRepo.findByName(employeeDTO.getName()).isEmpty())
//            throw new DuplicatedResourceException();

        return this.objToDTO(employeeService
                .save(this.dtoToObj(employeeDTO)));
    }

    /**
     * Updates an employee
     *
     * @param employeeDTO A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public EmployeeDTO update(EmployeeDTO employeeDTO) {
        return this.objToDTO(employeeService
                .update(this.dtoToObj(employeeDTO)));
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        employeeService.delete(id);
    }

    public EmployeeDTO findById(long employeeId) {
        EmployeeDTO employeeDTO = this
                .objToDTO(employeeService.findById(employeeId));
        return employeeDTO;
    }

    public List<EmployeeDTO> getAvailability(EmployeeRequestDTO employeeRequestDTO) {
        List<Employee> employees = employeeService
                .getAvailability(employeeRequestDTO);
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(employee -> this.objToDTO(employee))
                .collect(Collectors.toList());
        return employeeDTOs;
    }


    public EmployeeDTO saveAvailability(Long employeeId,
                                        Set<DayOfWeek> daysAvailable) {
        return this.objToDTO(employeeService
                .saveAvailability(employeeId, daysAvailable));
    }

    /**
     * Converts Obj to DTO
     *
     * @param employee to be converted
     * @return employeeDTO
     */
    public EmployeeDTO objToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        if (employee.getId() != null)
            employeeDTO.setId(employee.getId());
        if (employee.getName() != null)
            employeeDTO.setName(employee.getName());
        if (employee.getSkills() != null)
            employeeDTO.setSkills(employee.getSkills());
        if (employee.getDaysAvailable() != null)
            employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }

    /**
     * Converto DTO to Obj
     *
     * @param employeeDTO the be converted
     */
    public Employee dtoToObj(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        if (employeeDTO.getId() != null)
            employee.setId(employeeDTO.getId());
        if (employeeDTO.getName() != null)
            employee.setName(employeeDTO.getName());
        if (employeeDTO.getSkills() != null)
            employee.setSkills(employeeDTO.getSkills());
        if (employeeDTO.getDaysAvailable() != null)
            employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }
}
