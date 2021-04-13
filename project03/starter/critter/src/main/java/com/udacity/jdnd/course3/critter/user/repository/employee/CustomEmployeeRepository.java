package com.udacity.jdnd.course3.critter.user.repository.employee;

import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomEmployeeRepository {

    List<Employee> findAllFetchBySkillAndDaysAvailable(Long id);
}
