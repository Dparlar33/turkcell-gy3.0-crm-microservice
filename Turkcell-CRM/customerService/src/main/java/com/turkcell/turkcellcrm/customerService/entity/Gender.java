package com.turkcell.turkcellcrm.customerService.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE (0),
    FEMALE (1),
    OTHER(2);

    private final int value;

    public static Gender fromValue(int value) {
        for (Gender gender : Gender.values()) {
            if (gender.getValue() == value) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender value: " + value);
    }
}
