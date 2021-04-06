package com.udacity.jdnd.course3.critter.user.service;

import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.exception.custom.CouldNotBeNullException;
import com.udacity.jdnd.course3.critter.exception.custom.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.repository.employee.EmployeeRepository;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    /**
     * Gathers a list of all employees
     *
     * @return a list of all vehicles in the CarRepository
     */
    public List<Employee> findAll() {
        List<Employee> employees = employeeRepo.findAll();
        return employees;
    }

    /**
     * Gets Object information by ID (or throws exception if non-existent)
     *
     * @param id the ID number to gather information on
     * @return the requested information
     */
    public Employee findById(Long id) {
        Optional<Employee> obj = employeeRepo.findById(id);
        if (obj.isEmpty())
            throw new ResourceNotFoundException();

        return obj.get();
    }

    /**
     * Gets Object information by ID (or throws exception if non-existent)
     *
     * @param id the ID number to gather information on
     * @return the requested information
     */
    public Employee findAllFetchBySkillAndDaysAvailable(Long id) {
        Employee employee = employeeRepo
                .findAllFetchBySkillAndDaysAvailable(id).get(0);
        if (employee == null)
            throw new ResourceNotFoundException();

        return employee;
    }

    /**
     * Creates an Employee
     *
     * @param employee A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Employee save(Employee employee) {
        if (employee.getName() == null)
            throw new CouldNotBeNullException();
        return employeeRepo.save(employee);
    }

    /**
     * Updates an employee
     *
     * @param employee A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Employee update(Employee employee) {
        return employeeRepo.findByName(employee.getName())
                .map(objToBeUpdated -> {
                    if (employee.getName() != null)
                        objToBeUpdated.setName(employee.getName());
                    if (employee.getSkills() != null)
                        objToBeUpdated.setSkills(employee.getSkills());
                    if (employee.getDaysAvailable() != null)
                        objToBeUpdated.setDaysAvailable(employee.getDaysAvailable());
                    return employeeRepo.save(objToBeUpdated);
                }).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Optional<Employee> obj = employeeRepo.findById(id);
        if (obj.isEmpty())
            throw new ResourceNotFoundException();

        employeeRepo.delete(obj.get());
    }

    /**
     * Fetch Employee Availabity
     *
     * @param employeeRequestDTO
     */
    public List<Employee> getAvailability(EmployeeRequestDTO employeeRequestDTO) {

        List<EmployeeSkill> skills = new ArrayList<>(employeeRequestDTO.getSkills());
        List<DayOfWeek> days = new ArrayList<>();
        days.add(employeeRequestDTO.getDate().getDayOfWeek());

        List<Employee> employees = employeeRepo.findByDaysAvailableAndSkills(days, skills);
        return employees;
    }

    /**
     * Save/Update Employee availability
     *
     * @param employeeId
     * @param daysAvailable
     * @return Employee
     */
    public Employee saveAvailability(Long employeeId, Set<DayOfWeek> daysAvailable) {
        Employee employee = this.findAllFetchBySkillAndDaysAvailable(employeeId);
        employee.setDaysAvailable(daysAvailable);
        return employeeRepo.save(employee);
    }

    /**
     * Finda All by Ids
     *
     * @param employeeIds
     * @return List<Employee>
     */
    public List<Employee> findAllByIds(List<Long> employeeIds) {
        return employeeRepo.findAllById(employeeIds);
    }
}
