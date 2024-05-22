package com.cotech.expensetracking.controller;

import com.cotech.expensetracking.model.auth.AuthResponse;
import com.cotech.expensetracking.model.auth.Login;
import com.cotech.expensetracking.model.auth.Registration;
import com.cotech.expensetracking.service.UserAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {

    private final UserAuthService authService;

    public AuthController(final UserAuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> registerUser(final @RequestBody Registration registration) {
        return this.authService.createUser(registration);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> loginUser(final @RequestBody Login login) {
        return this.authService.loginUser(login);
    }

//    @PostMapping(value = "/token")
//    public ResponseEntity<String> getUserJwtToken(final @RequestBody UserAuth userAuth) {
//        return this.authService.loginUser(userAuth);
//    }
}
