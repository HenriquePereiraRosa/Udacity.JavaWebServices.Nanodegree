package com.udacity.jdnd.course3.critter.user.repository.employee;

import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, CustomEmployeeRepository {
    Optional<Employee> findByName(String name);

    @Query("from Employee e where e.daysAvailable in :day ")
    List<Employee> findQueryByDaysAvailable(@Param("day") DayOfWeek daysOfWeek);

    List<Employee> findAllByDaysAvailableContaining(DayOfWeek dayOfWeek);
}
