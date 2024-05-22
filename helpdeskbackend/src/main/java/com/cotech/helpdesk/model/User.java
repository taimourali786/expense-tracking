package com.cotech.helpdesk.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String firstName;
    private String lastName;
    private String address;
    private int age;
}
