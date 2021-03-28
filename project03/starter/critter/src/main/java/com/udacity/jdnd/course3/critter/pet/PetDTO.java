package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerDTO;

import java.time.LocalDate;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
public class PetDTO {
    private Long id;
    private PetType type;
    private String name;
    private CustomerDTO ownerDto;
    private LocalDate birthDate;
    private String notes;

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerDTO getOwnerDto() {
        return ownerDto;
    }

    public void setOwnerDto(CustomerDTO ownerDto) {
        this.ownerDto = ownerDto;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
