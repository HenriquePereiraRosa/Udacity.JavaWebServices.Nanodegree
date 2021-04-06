package com.udacity.jdnd.course3.critter.user.repository.employee;

import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, CustomEmployeeRepository {
    Optional<Employee> findByName(String name);

    @Query("from Employee e where e.daysAvailable in (:days) and e.skills in (:skills) ")
    List<Employee> findByDaysAvailableAndSkills(@Param("days") Set<DayOfWeek> daysOfWeek,
                                                @Param("skills") Set<EmployeeSkill> skills);
}
