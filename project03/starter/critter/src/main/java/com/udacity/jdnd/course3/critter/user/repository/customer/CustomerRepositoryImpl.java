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
import java.util.Date;
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


            // todo : DEBUG Col Names
            String colNames = "";
            int count = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= count; i++) {
                colNames += rs.getMetaData().getColumnName(i) + " | ";
            }

            Customer customer = new Customer();
            customer.setId(rs.getLong("id"));
            customer.setName(rs.getString("name"));
            customer.setNotes(rs.getString("notes"));
            customer.setPhoneNumber(rs.getString("phone_number"));

            Pet pet = new Pet();
            pet.setId(rs.getLong("id"));
            Date birthDate = rs.getDate("birth_date");
            if (birthDate != null)
                pet.setBirthDate(LocalDate.from(birthDate.toInstant()));
            pet.setName(rs.getString("name"));
            pet.setNotes(rs.getString("notes"));
            pet.setType(PetType.fromCode(rs.getInt("type")));
//            pet.setOwner(customer);

            List<Pet> pets = new ArrayList<>();
            pets.add(pet);
            customer.setPets(pets);

            return customer;
        };

        List<Customer> customers = jdbcTemplate.query(
                sQuery.toString(),
                new MapSqlParameterSource()
                        .addValue("petId", petId),
                rowMapper);

        return customers;
    }
}
