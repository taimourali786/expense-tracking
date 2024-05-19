package com.cotech.expensetracking.jpa.user;

import jakarta.persistence.*;

@Entity
@Table(schema = "expense_tracking", name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "age")
    private int age;
}
