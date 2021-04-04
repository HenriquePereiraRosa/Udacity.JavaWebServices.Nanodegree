package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.exception.custom.CouldNotBeNullException;
import com.udacity.jdnd.course3.critter.exception.custom.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepo;
    @Autowired
    private CustomerService customerService;


    /**
     * Gets All Customer information
     *
     * @return the requested information
     */
    public List<Pet> findAll() {
        List<Pet> pets = petRepo.findAll();
        return pets;
    }

    /**
     * Gets Pet information by ID (or throws exception if non-existent)
     *
     * @param id the ID number to gather information on
     * @return Pet
     */
    private Pet findById(Long id) {
        Optional<Pet> obj = petRepo.findById(id);
        if (obj.isEmpty())
            throw new ResourceNotFoundException();

        return obj.get();
    }

    /**
     * Gets Pet information by the ID of the Owner (or throws exception if non-existent)
     *
     * @param ownerId the ID number to gather information on
     * @return the requested information
     */
    public List<Pet> findAllByOwnerId(Long ownerId) {
        return petRepo.findAllByOwnerId(ownerId);
    }

    /**
     * Creates a pet on DB
     *
     * @param pet A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Pet save(Pet pet) {
        if (pet.getName() == null)
            throw new CouldNotBeNullException();
//        List<Pet> pets = petRepo.findByName(petDTO.getName());
//        if (!pets.isEmpty())
//            throw new DuplicatedResourceException();

        return petRepo.save(pet);
    }

    /**
     * Updates an pet
     *
     * @param pet A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Pet update(Pet pet) {

        Pet petDB = this.findById(pet.getId());

        if (pet.getType() != null)
            petDB.setType(pet.getType());
        if (pet.getName() != null)
            petDB.setName(pet.getName());
        if (pet.getOwner() != null)
            petDB.setOwner(customerService
                    .findById(pet.getOwner().getId()));
        if (pet.getBirthDate() != null)
            petDB.setBirthDate(pet.getBirthDate());
        if (pet.getNotes() != null)
            petDB.setNotes(pet.getNotes());
        return petRepo.save(petDB);
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Pet pet = this.findById(id);
        petRepo.delete(pet);
    }

    public List<Pet> findAllByIds(List<Long> employeeIds) {
        return petRepo.findAllByIds(employeeIds);
    }
}
