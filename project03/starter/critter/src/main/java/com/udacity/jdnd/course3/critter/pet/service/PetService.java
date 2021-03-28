package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.exception.custom.CouldNotBeNullException;
import com.udacity.jdnd.course3.critter.exception.custom.NotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<PetDTO> findAll() {
        List<PetDTO> petsDTO = new ArrayList<>();
        List<Pet> pets = petRepo.findAll();
        pets.forEach(pet -> petsDTO.add(this.objToDTO(pet)));
        return petsDTO;
    }

    /**
     * Gets Pet information by ID (or throws exception if non-existent)
     *
     * @param id the ID number to gather information on
     * @return Pet
     */
    private Pet findByIdPriv(Long id) {
        Optional<Pet> obj = petRepo.findById(id);
        if (obj.isEmpty())
            throw new NotFoundException();

        return obj.get();
    }

    /**
     * Gets Pet information by ID (or throws exception if non-existent)
     *
     * @param id the ID number to gather information on
     * @return the requested information
     */
    public PetDTO findById(Long id) {
        return this.objToDTO(this.findByIdPriv(id));
    }

    /**
     * Gets Pet information by the ID of the Owner (or throws exception if non-existent)
     *
     * @param ownerId the ID number to gather information on
     * @return the requested information
     */
    public List<PetDTO> findAllByOwnerId(Long ownerId) {
        return petRepo.findAllByOwnerId(ownerId).stream()
                .map(pet -> this.objToDTO(pet))
                .collect(Collectors.toList());
    }

    /**
     * Creates a pet on DB
     *
     * @param petDTO A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public PetDTO save(PetDTO petDTO) {
        if (petDTO.getName() == null)
            throw new CouldNotBeNullException();
//        List<Pet> pets = petRepo.findByName(petDTO.getName());
//        if (!pets.isEmpty())
//            throw new DuplicatedResourceException();

        return this.objToDTO(petRepo.save(this.dtoToObj(petDTO)));
    }

    /**
     * Updates an customer
     *
     * @param petDTO A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public PetDTO update(PetDTO petDTO) {

        Pet pet = this.findByIdPriv(petDTO.getId());

        if (petDTO.getType() != null)
            pet.setType(petDTO.getType());
        if (petDTO.getName() != null)
            pet.setName(petDTO.getName());
        if (petDTO.getOwnerDto() != null)
            pet.setOwner(customerService
                    .dtoToObj(petDTO.getOwnerDto()));
        if (petDTO.getBirthDate() != null)
            pet.setBirthDate(petDTO.getBirthDate());
        if (petDTO.getNotes() != null)
            pet.setNotes(petDTO.getNotes());
        return this.objToDTO(petRepo.save(pet));
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Pet pet = this.findByIdPriv(id);
        petRepo.delete(pet);
    }

    /**
     * Converto DTO to Obj
     *
     * @param pet the be converted
     */
    public PetDTO objToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        if (pet.getId() != null)
            petDTO.setId(pet.getId());
        if (pet.getType() != null)
            petDTO.setType(pet.getType());
        if (pet.getName() != null)
            petDTO.setName(pet.getName());
        if (pet.getOwner() != null)
            petDTO.setOwnerDto(customerService
                    .objToDTO(pet.getOwner()));
        if (pet.getBirthDate() != null)
            petDTO.setBirthDate(pet.getBirthDate());
        if (pet.getNotes() != null)
            petDTO.setNotes(pet.getNotes());

        return petDTO;
    }

    /**
     * Converto DTO to Obj
     *
     * @param petDTO the be converted
     */
    public Pet dtoToObj(PetDTO petDTO) {
        Pet pet = new Pet();
        if (petDTO.getId() != null)
            pet.setId(petDTO.getId());
        if (petDTO.getType() != null)
            pet.setType(petDTO.getType());
        if (petDTO.getName() != null)
            pet.setName(petDTO.getName());
        if (petDTO.getOwnerDto() != null)
            pet.setOwner(customerService
                    .dtoToObj(petDTO.getOwnerDto()));
        if (petDTO.getBirthDate() != null)
            pet.setBirthDate(petDTO.getBirthDate());
        if (petDTO.getNotes() != null)
            pet.setNotes(petDTO.getNotes());

        return pet;
    }

}
