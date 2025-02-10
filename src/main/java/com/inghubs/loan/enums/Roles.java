package com.inghubs.loan.enums;

import lombok.Getter;

@Getter
public enum Roles {

    ADMIN("ROLE_ADMIN"),
    CUSTOMER("ROLE_CUSTOMER");

    private final String definition;

    Roles(String definition) {
        this.definition = definition;
    }

}
