package com.cotech.helpdesk.jpa.userauth;

import lombok.Getter;

@Getter
public enum Role {
    USER("User"),
    ADMIN("ADMIN");
    private final String name;

    Role(final String name) {
        this.name = name;
    }

}
