package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetType;

/**
 * A example list of employee skills that could be included on an employee or a schedule request.
 */
public enum EmployeeSkill {
    PETTING(0),
    WALKING(1),
    FEEDING(2),
    MEDICATING(3),
    SHAVING(4);

    private Integer code;

    EmployeeSkill(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static EmployeeSkill fromCode(Integer number) {
        for (EmployeeSkill item : EmployeeSkill.values()) {
            if (item.getCode().compareTo(number) == 0)
                return item;
        }
        return null;
    }
}
