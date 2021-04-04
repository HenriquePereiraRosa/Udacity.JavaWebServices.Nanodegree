package com.udacity.jdnd.course3.critter.user.repository.employee;

import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByName(String name);

    @Query("from Employee e where e.daysAvailable = :day and e.skills in (:skills) ")
    List<Employee> findByDaysAvailableAndSkills(@Param("day") DayOfWeek dayOfWeek,
                                                @Param("skills") Set<EmployeeSkill> skills);

//    List<Employee> findByDaysAvailableAndSkills(DayOfWeek daysOfWeek,
//                                                Set<EmployeeSkill> skills);
}
