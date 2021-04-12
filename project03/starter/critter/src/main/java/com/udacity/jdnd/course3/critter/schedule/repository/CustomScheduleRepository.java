package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomScheduleRepository {
    List<Schedule> findAllFetchPets();
    List<Schedule> findAllFetchAll();

    List<Schedule> findAllByPetId(Long petId);
}
