package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleRepositoryImpl implements CustomScheduleRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Schedule> findAllFetchPets() {

        StringBuilder sQuery = new StringBuilder("select * " +
                " from Schedule s " +
                "   left join Pet p on s.id = p.schedule_id ");

        RowMapper rowMapper = (rs, rowNum) -> {


            // todo : DEBUG Col Names
            String colNames = "";
            int count = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= count; i++) {
                colNames += rs.getMetaData().getColumnName(i) + " | ";
            }

            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            Date date = rs.getDate("date");
            if (date != null)
                schedule.setDate(Instant.ofEpochMilli(date.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());

            Pet pet = new Pet();
            pet.setId(rs.getLong("id"));
            Date birthDate = rs.getDate("birth_date");
            if (birthDate != null)
                pet.setBirthDate(LocalDate.from(birthDate.toInstant()));
            pet.setName(rs.getString("name"));
            pet.setNotes(rs.getString("notes"));
            pet.setType(PetType.fromCode(rs.getInt("type")));

            List<Pet> pets = new ArrayList<>();
            pets.add(pet);
            schedule.setPets(pets);

            return schedule;
        };

        List<Schedule> schedules = jdbcTemplate.query(
                sQuery.toString(),
                new MapSqlParameterSource(),
                rowMapper);

        return schedules;
    }

    @Override
    public List<Schedule> findAllFetchAll() {

        StringBuilder sQuery = new StringBuilder("select * " +
                " from Schedule s " +
                "   left join Pet p on s.id = p.schedule_id ");

        RowMapper rowMapper = (rs, rowNum) -> {


            // todo : DEBUG Col Names
            String colNames = "";
            int count = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= count; i++) {
                colNames += rs.getMetaData().getColumnName(i) + " | ";
            }

            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            Date date = rs.getDate("date");
            if (date != null)
                schedule.setDate(Instant.ofEpochMilli(date.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());

            Pet pet = new Pet();
            pet.setId(rs.getLong("id"));
            Date birthDate = rs.getDate("birth_date");
            if (birthDate != null)
                pet.setBirthDate(LocalDate.from(birthDate.toInstant()));
            pet.setName(rs.getString("name"));
            pet.setNotes(rs.getString("notes"));
            pet.setType(PetType.fromCode(rs.getInt("type")));

            List<Pet> pets = new ArrayList<>();
            pets.add(pet);
            schedule.setPets(pets);

            return schedule;
        };

        List<Schedule> schedules = jdbcTemplate.query(
                sQuery.toString(),
                new MapSqlParameterSource(),
                rowMapper);

        return schedules;
    }

    @Override
    public List<Schedule> cFindAllByPetId(Long petId) {

        StringBuilder sQuery = new StringBuilder("select * " +
                " from Schedule s " +
                "   join Pet p on s.id = p.schedule_id " +
                " where p.id = :petId");

        RowMapper rowMapper = (rs, rowNum) -> {


            // todo : DEBUG Col Names
            String colNames = "";
            int count = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= count; i++) {
                colNames += rs.getMetaData().getColumnName(i) + " | ";
            }

            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            Date date = rs.getDate("date");
            if (date != null)
                schedule.setDate(Instant.ofEpochMilli(date.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());

            Pet pet = new Pet();
            pet.setId(rs.getLong("id"));
            Date birthDate = rs.getDate("birth_date");
            if (birthDate != null)
                pet.setBirthDate(LocalDate.from(birthDate.toInstant()));
            pet.setName(rs.getString("name"));
            pet.setNotes(rs.getString("notes"));
            pet.setType(PetType.fromCode(rs.getInt("type")));

            List<Pet> pets = new ArrayList<>();
            pets.add(pet);
            schedule.setPets(pets);

            return schedule;
        };

        List<Schedule> schedules = jdbcTemplate.query(
                sQuery.toString(),
                new MapSqlParameterSource()
                        .addValue("petId", petId),
                rowMapper);

        return schedules;
    }
}
