package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleDTOHelper {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PetService petService;


    /**
     * Gets All Schedule information
     *
     * @return the requested information
     */
    public List<ScheduleDTO> findAll() {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        List<Schedule> schedules = scheduleService.findAll();
        for (Schedule schedule : schedules)
            schedulesDTO.add(this.objToDTO(schedule));
        return schedulesDTO;
    }

    /**
     * Gets Schedule information by ID (or throws exception if non-existent)
     *
     * @param id the ID number to gather information on
     * @return the requested information
     */
    public List<ScheduleDTO> findDTOById(Long id) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        List<Schedule> schedules = scheduleService.findById(id);
        for (Schedule schedule : schedules)
            schedulesDTO.add(this.objToDTO(schedule));
        return schedulesDTO;
    }


    /**
     * Gets Schedule information by ID (or throws exception if non-existent)
     *
     * @param employeeId the ID of the employee
     * @return a list of scheduleDTO from given employee
     */
    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        List<Schedule> schedules = scheduleService
                .getScheduleForEmployee(employeeId);
        for (Schedule schedule : schedules)
            schedulesDTO.add(this.objToDTO(schedule));
        return schedulesDTO;
    }


    /**
     * Gets Schedule information by ID (or throws exception if non-existent)
     *
     * @param customerId the ID of the customer
     * @return A list of ScheduleDTO from given customer
     */
    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        for (Schedule schedule : schedules)
            schedulesDTO.add(this.objToDTO(schedule));
        return schedulesDTO;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     *
     * @param scheduleDTO A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public ScheduleDTO save(ScheduleDTO scheduleDTO) {
        return this.objToDTO(scheduleService
                .save(this.dtoToObj(scheduleDTO)));
    }

    /**
     * Updates an Schedule
     *
     * @param scheduleDTO A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public ScheduleDTO update(ScheduleDTO scheduleDTO) {
        return this.objToDTO(scheduleService
                .update(this.dtoToObj(scheduleDTO)));
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        scheduleService.delete(id);
    }

    /**
     * Converts Obj to DTO
     *
     * @param schedule to be converted
     * @return scheduleDTO
     */
    public ScheduleDTO objToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        if (schedule.getId() != null)
            scheduleDTO.setId(schedule.getId());

        if (schedule.getEmployees() != null && !schedule.getEmployees().isEmpty())
            scheduleDTO.setEmployeeIds(schedule.getEmployees().stream()
                    .map(Employee::getId)
                    .collect(Collectors.toList()));

        if (schedule.getActivities() != null && !schedule.getActivities().isEmpty())
            scheduleDTO.setActivities(schedule.getActivities());

        if (schedule.getDate() != null)
            scheduleDTO.setDate(schedule.getDate());

        if (schedule.getPets() != null && !schedule.getPets().isEmpty())
            scheduleDTO.setPetIds(schedule.getPets().stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList()));

        return scheduleDTO;
    }

    /**
     * Converto DTO to Obj
     *
     * @param scheduleDTO the be converted
     * @return schedule converted
     */
    public Schedule dtoToObj(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();

        if (scheduleDTO.getId() != null)
            schedule.setId(scheduleDTO.getId());

        if (scheduleDTO.getEmployeeIds() != null
                && !scheduleDTO.getEmployeeIds().isEmpty())
            schedule.setEmployees(employeeService
                    .findAllByIds(scheduleDTO.getEmployeeIds()));

        if (scheduleDTO.getActivities() != null
                && !scheduleDTO.getActivities().isEmpty())
            schedule.setActivities(scheduleDTO.getActivities());

        if (scheduleDTO.getDate() != null)
            schedule.setDate(scheduleDTO.getDate());

        if (scheduleDTO.getPetIds() != null
                && !scheduleDTO.getPetIds().isEmpty())
            schedule.setPets(petService
                    .findAllByIds(scheduleDTO.getEmployeeIds()));

        return schedule;
    }
}
