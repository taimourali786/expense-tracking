package com.cotech.helpdesk.jpa.priority;

public enum PriorityEnum {
    LOW("Low"),
    NORMAL("Normal"),
    HIGH("High"),
    CRITICAL("Critical");

    private String name;

    PriorityEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
