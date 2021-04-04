package com.udacity.jdnd.course3.critter.pet.repository;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findOneByName(String name);

    List<Pet> findByName(String name);

    @Query("from Pet p where p.owner.id = :ownerId")
    List<Pet> findAllByOwnerId(@Param("ownerId") Long ownerId);

    @Query("from Pet p where p.id in (:ids)")
    List<Pet> findAllByIds(@Param("ids") List<Long> petIds);
}
