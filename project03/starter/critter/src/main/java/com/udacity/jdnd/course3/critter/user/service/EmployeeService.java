package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.exception.custom.CouldNotBeNullException;
import com.udacity.jdnd.course3.critter.exception.custom.DuplicatedResourceException;
import com.udacity.jdnd.course3.critter.exception.custom.NotFoundException;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    /**
     * Gathers a list of all vehicles
     *
     * @return a list of all vehicles in the CarRepository
     */
    public List<EmployeeDTO> findAll() {
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        List<Employee> employees = employeeRepo.findAll();
        employees.forEach(employee -> employeeDTOs.add(this.objToDTO(employee)));
        return employeeDTOs;
    }

    /**
     * Gets Customer information by ID (or throws exception if non-existent)
     *
     * @param id the ID number to gather information on
     * @return the requested information
     */
    public EmployeeDTO findById(Long id){
        Optional<Employee> obj = employeeRepo.findById(id);
        if (obj.isEmpty())
            throw new NotFoundException();

        return this.objToDTO(obj.get());
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
        if (employeeRepo.findByName(employeeDTO.getName()).isEmpty())
            throw new DuplicatedResourceException();

        return this.objToDTO(employeeRepo.save(this.dtoToObj(employeeDTO)));
    }

    /**
     * Updates an employee
     *
     * @param employeeDTO A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public EmployeeDTO update(EmployeeDTO employeeDTO) {
        return this.objToDTO(employeeRepo.findByName(employeeDTO.getName())
                .map(objToBeUpdated -> {
                    if (employeeDTO.getName() != null)
                        objToBeUpdated.setName(employeeDTO.getName());
                    if (employeeDTO.getSkills() != null)
                        objToBeUpdated.setSkills(employeeDTO.getSkills());
                    if (employeeDTO.getDaysAvailable() != null)
                        objToBeUpdated.setDaysAvailable(employeeDTO.getDaysAvailable());
                    return employeeRepo.save(objToBeUpdated);
                }).orElseThrow(NotFoundException::new));
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Optional<Employee> obj = employeeRepo.findById(id);
        if (obj.isEmpty())
            throw new NotFoundException();

        employeeRepo.delete(obj.get());
    }

    public EmployeeDTO findById(long employeeId) {
        EmployeeDTO employeeDTO = this.objToDTO(employeeRepo.findById(employeeId).get());
        if (employeeDTO.getId() == null)
            throw new NotFoundException();
        return employeeDTO;
    }

    public List<EmployeeDTO> getAvailability(EmployeeRequestDTO employeeRequestDTO) {
        Set<DayOfWeek> daysOfWeek = new HashSet<>();
        daysOfWeek.add(employeeRequestDTO.getDate().getDayOfWeek());
        List<Employee> employees = employeeRepo
                .findAllByAvailabilityAndSkills(daysOfWeek, employeeRequestDTO.getSkills());
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(employee -> this.objToDTO(employee))
                .collect(Collectors.toList());
        return employeeDTOs;
    }


    public void saveAvailability(long employeeId, Set<DayOfWeek> daysAvailable) {
        EmployeeDTO employeeDTO = this.findById(employeeId);
        employeeDTO.setDaysAvailable(daysAvailable);
        employeeRepo.save(this.dtoToObj(employeeDTO));
    }

    /**
     * Converts Obj to DTO
     *
     * @param employee to be converted
     * @return employeeDTO
     */
    public EmployeeDTO objToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        if (employeeDTO.getId() != null)
            employeeDTO.setId(employee.getId());
        if (employeeDTO.getName() != null)
            employeeDTO.setName(employee.getName());
        if (employeeDTO.getSkills() != null)
            employeeDTO.setSkills(employee.getSkills());
        if (employeeDTO.getDaysAvailable() != null)
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
