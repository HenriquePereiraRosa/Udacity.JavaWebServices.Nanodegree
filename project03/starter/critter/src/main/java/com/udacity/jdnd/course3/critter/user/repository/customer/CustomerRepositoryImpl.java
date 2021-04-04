package com.udacity.jdnd.course3.critter.user.repository.customer;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomCustomerRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Customer> findCustomerByPetId(Long petId) {

        StringBuilder sQuery = new StringBuilder("select * " +
                " from Customer c " +
                "   inner join Pet p on c.id = p.owner_id " +
                "   where p.id = :petId");

        RowMapper rowMapper = (rs, rowNum) -> {

            Customer customer = new Customer();
            customer.setId(rs.getLong("c.id"));
            customer.setName(rs.getString("c.name"));
            customer.setNotes(rs.getString("c.notes"));
            customer.setPhoneNumber(rs.getString("c.phone_number"));

            Pet pet = new Pet();
            pet.setId(rs.getLong("p.id"));
            pet.setBirthDate(LocalDate
                    .from(rs.getDate("p.birth_date").toInstant()));
            pet.setName(rs.getString("p.name"));
            pet.setNotes(rs.getString("p.notes"));
            pet.setType(PetType.fromCode(rs.getInt("p.type")));
            pet.setOwner(customer);

            List<Pet> pets = new ArrayList<>();
            pets.add(pet);
            customer.setPets(pets);

            return customer;
        };

        List<Customer> customers = jdbcTemplate.query(
                sQuery.toString(),
                new MapSqlParameterSource(),
                rowMapper);

        return customers;
    }
}
