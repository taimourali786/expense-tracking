package com.cotech.expensetracking.model;

import lombok.Data;

@Data
public class UserAuth {

    private String email;
    private String password;
    private User user;
}
