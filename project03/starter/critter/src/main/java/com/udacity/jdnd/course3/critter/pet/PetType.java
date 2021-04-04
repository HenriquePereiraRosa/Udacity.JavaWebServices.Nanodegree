package com.udacity.jdnd.course3.critter.pet;

/**
 * A example list of pet type metadata that could be included on a request to create a pet.
 */
public enum PetType {
    CAT(0),
    DOG(1),
    LIZARD(2),
    BIRD(3),
    FISH(4),
    SNAKE(5),
    OTHER(6);

    private Integer code;

    PetType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static PetType fromCode(Integer number) {
        for (PetType item : PetType.values()) {
            if (item.getCode().compareTo(number) == 0)
                return item;
        }
        return null;
    }
}
