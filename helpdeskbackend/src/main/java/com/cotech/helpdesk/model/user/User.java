package com.cotech.helpdesk.model.user;

import com.cotech.helpdesk.model.Department;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String firstName;
    private String lastName;
    private String address;
    private Long phoneNumber;
    private int age;
    private Department department;
}
