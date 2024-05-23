package com.cotech.helpdesk.jpa.status;

public enum StatusEnum {
    PENDING("Pending"),
    IN_PROGRESS("In-progress"),
    DONE("Done");

    private String name;

    StatusEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
