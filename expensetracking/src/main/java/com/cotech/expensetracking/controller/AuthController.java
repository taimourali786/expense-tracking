package com.cotech.expensetracking.controller;

import com.cotech.expensetracking.model.UserAuth;
import com.cotech.expensetracking.service.UserAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/")
public class AuthController {

    private final UserAuthService authService;

    public AuthController(final UserAuthService authService){
        this.authService = authService;
    }
    @PostMapping(value = "/registration")
    public ResponseEntity<String> registerUser(final @RequestBody UserAuth userAuth){
        return this.authService.createUser(userAuth);
    }
}
