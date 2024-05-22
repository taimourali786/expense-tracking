package com.cotech.expensetracking.service;


import com.cotech.expensetracking.jpa.user.UserEntity;
import com.cotech.expensetracking.jpa.user.UserRepository;
import com.cotech.expensetracking.jpa.userauth.Role;
import com.cotech.expensetracking.jpa.userauth.UserAuthEntity;
import com.cotech.expensetracking.jpa.userauth.UserAuthRepository;
import com.cotech.expensetracking.model.User;
import com.cotech.expensetracking.model.auth.AuthResponse;
import com.cotech.expensetracking.model.auth.Login;
import com.cotech.expensetracking.model.auth.Registration;
import com.cotech.expensetracking.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final UserAuthRepository userAuthRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<AuthResponse> createUser(final Registration registration) {
        UserAuthEntity entity = this.userAuthRepository
                .findByEmail(registration.getEmail())
                .orElse(null);
        if (entity != null) {
            String token = this.jwtService.generateToken(entity);
            return new ResponseEntity<>(new AuthResponse(token), HttpStatus.BAD_REQUEST);
        }
        User user = User.builder()
                .firstName(registration.getFirstName())
                .lastName(registration.getLastName())
                .address(registration.getAddress())
                .age(registration.getAge())
                .build();
        UserEntity userEntity = this.modelMapper.map(user, UserEntity.class);
        this.userRepository.save(userEntity);
        UserAuthEntity authEntity = UserAuthEntity
                .builder()
                .email(registration.getEmail())
                .password(this.passwordEncoder.encode(registration.getPassword()))
                .role(Role.USER)
                .user(userEntity)
                .build();
        this.userAuthRepository.save(authEntity);
        String token = this.jwtService.generateToken(authEntity);
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.CREATED);
    }

    public ResponseEntity<AuthResponse> loginUser(final Login login) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(),
                        login.getPassword()));

        UserAuthEntity entity = this.userAuthRepository
                .findByEmail(login.getEmail())
                .orElse(null);
        String token = this.jwtService.generateToken(entity);
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.CREATED);
    }
}
