package com.udacity.jdnd.course3.critter.user.repository.customer;

import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findOneByName(String name);
    List<Customer> findByName(String name);

    @Query("select c from Customer c left join fetch c.pets")
    List<Customer> findAllFetchByPets();


    @Query("select c from Customer c left join fetch c.pets p" +
            " where p.id = :petId")
    Customer findCustomerByPetId(@Param("petId") Long petId);
}
