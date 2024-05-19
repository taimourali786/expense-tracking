package com.cotech.expensetracking.jpa.userauth;


import com.cotech.expensetracking.jpa.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(schema = "expense_tracking", name = "user_authorization")
public class UserAuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name="email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
}
