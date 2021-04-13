package com.udacity.jdnd.course3.critter.user.repository.employee;

import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeRepositoryImpl implements CustomEmployeeRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager em;


    /**
     * Fetch Employee with Skills and DaysAvailable
     * by employeeId
     *
     * @param id
     * @return employees
     */
    @Override
    public List<Employee> findAllFetchBySkillAndDaysAvailable(Long id) {

        List<Employee> employees = new ArrayList<>();

        StringBuilder sQuery = new StringBuilder("select * " +
                " from employee e " +
                " left join employee_days_available eda on e.id = eda.employee_id " +
                " left join employee_skills es on e.id = es.employee_id " +
                "   where e.id = :id");

        RowMapper rowMapper = (rs, rowNum) -> {

            // todo : DEBUG Col Names
            String colNames = "";
            int count = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= count; i++) {
                colNames += rs.getMetaData().getColumnName(i) + " | ";
            }

            Employee employee = new Employee();
            employee.setId(rs.getLong("id"));
            employee.setName(rs.getString("name"));


            if (employees.contains(employee)) {
                employee = employees.get(employees.indexOf(employee));
            } else {
                employees.add(employee);
            }

            Integer skillInt = rs.getInt("skills");
            if (skillInt == (int) skillInt) {
                EmployeeSkill skill = EmployeeSkill.fromCode(skillInt);
                if (employee.getSkills() == null
                        || employee.getSkills().isEmpty()) {

                    Set<EmployeeSkill> skills = new HashSet<>();
                    employee.setSkills(skills);
                }
                employee.getSkills().add(skill);
            }


            String dayStr = rs.getString("days_available");
            if (dayStr != null && !dayStr.isEmpty()) {
                DayOfWeek day = DayOfWeek.valueOf(dayStr);
                if (employee.getDaysAvailable() == null
                        || employee.getDaysAvailable().isEmpty()) {

                    Set<DayOfWeek> daysOfWeek = new HashSet<>();
                    employee.setDaysAvailable(daysOfWeek);
                }
                employee.getDaysAvailable().add(day);
            }

            return null;
        };

        jdbcTemplate.query(
                sQuery.toString(),
                new MapSqlParameterSource()
                        .addValue("id", id),
                rowMapper);

        return employees;
    }

  }
