package com.cotech.helpdesk.service;


import com.cotech.helpdesk.jpa.user.UserEntity;
import com.cotech.helpdesk.jpa.user.UserRepository;
import com.cotech.helpdesk.jpa.userauth.Role;
import com.cotech.helpdesk.jpa.userauth.UserAuthEntity;
import com.cotech.helpdesk.jpa.userauth.UserAuthRepository;
import com.cotech.helpdesk.model.user.User;
import com.cotech.helpdesk.model.auth.AuthResponse;
import com.cotech.helpdesk.model.auth.Login;
import com.cotech.helpdesk.model.auth.Registration;
import com.cotech.helpdesk.security.service.JwtService;
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
