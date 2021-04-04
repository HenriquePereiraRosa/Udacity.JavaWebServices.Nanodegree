package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.exception.custom.CouldNotBeNullException;
import com.udacity.jdnd.course3.critter.exception.custom.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepo;


    /**
     * Gets All Schedule information
     *
     * @return the requested information
     */
    public List<Schedule> findAll() {
        List<Schedule> schedules = scheduleRepo.findAll();
        return schedules;
    }

    /**
     * Gets Schedule information by ID (or throws exception if non-existent)
     *
     * @param id the ID number to gather information on
     * @return the requested information
     */
    public List<Schedule> findById(Long id) {
        Optional<Schedule> obj = scheduleRepo.findById(id);
        if (obj.isEmpty())
            throw new ResourceNotFoundException();
        List<Schedule> ret = new ArrayList<>();
        ret.add(obj.get());
        return ret;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     *
     * @param schedule A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Schedule save(Schedule schedule) {
        if (schedule.getDate() == null)
            throw new CouldNotBeNullException();
        return scheduleRepo.save(schedule);
    }

    /**
     * Updates an Schedule
     *
     * @param schedule A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Schedule update(Schedule schedule) {
        Optional<Schedule> scheduleOpt = scheduleRepo.findById(schedule.getId());
        if (scheduleOpt.isEmpty())
            throw new ResourceNotFoundException();

        Schedule scheduleDb = scheduleOpt.get();
        if (schedule.getDate() != null)
            scheduleDb.setDate(schedule.getDate());
        if (schedule.getActivities() != null && !schedule.getActivities().isEmpty())
            if (scheduleDb.getActivities().isEmpty())
                scheduleDb.setActivities(new HashSet<>());
        schedule.getActivities().addAll(schedule.getActivities());
        if (schedule.getEmployees() != null && !schedule.getEmployees().isEmpty())
            if (scheduleDb.getEmployees().isEmpty())
                scheduleDb.setEmployees(new ArrayList<>());
        schedule.getEmployees().addAll(schedule.getEmployees());

        return scheduleDb;
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Optional<Schedule> obj = scheduleRepo.findById(id);
        if (obj.isEmpty())
            throw new ResourceNotFoundException();
        scheduleRepo.delete(obj.get());
    }

    public List<Schedule> getScheduleForEmployee(Long employeeId) {
        return scheduleRepo.findAllByEmployeesId(employeeId);
    }

    public List<Schedule> getScheduleForCustomer(Long customerId) {
        return scheduleRepo.findAllByCustomerId(customerId);
    }
}
