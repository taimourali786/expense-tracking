package com.cotech.helpdesk.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    private String firstName;
    private String lastName;
    private Integer age;
    private String address;
    private String email;
    private String password;
}
