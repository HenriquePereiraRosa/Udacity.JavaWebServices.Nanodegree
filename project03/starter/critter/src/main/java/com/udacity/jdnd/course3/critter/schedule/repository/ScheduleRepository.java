package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>, CustomScheduleRepository {

    @Query("from Schedule s where s.id  = id")
    List<Schedule> findAllById(@Param("id") Long id);

    @Query("from Schedule s left join fetch s.pets p" +
            " where p.id = :id")
    List<Schedule> findAllByPetsId(@Param("id") Long id);

    List<Schedule> findAllByEmployeesId(Long employeeId);

    @Query("from Schedule s join fetch s.pets p" +
            " where p.owner.id = :id")
    List<Schedule> findAllByCustomerId(@Param("id") Long customerId);
}
