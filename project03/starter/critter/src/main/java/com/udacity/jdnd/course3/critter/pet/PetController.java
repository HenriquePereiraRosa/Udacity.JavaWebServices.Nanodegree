package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.pet.service.PetDTOHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetDTOHelper petDTOHelper;

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return petDTOHelper.findById(petId);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petDTOHelper.findAll();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable Long ownerId) {
        return petDTOHelper.findAllByOwnerId(ownerId);
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return petDTOHelper.save(petDTO);
    }

    @PutMapping
    public PetDTO updatePet(@RequestBody PetDTO petDTO) {
        return petDTOHelper.update(petDTO);
    }
}
