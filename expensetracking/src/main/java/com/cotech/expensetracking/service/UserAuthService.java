package com.cotech.expensetracking.service;


import com.cotech.expensetracking.jpa.userauth.UserAuthEntity;
import com.cotech.expensetracking.jpa.userauth.UserAuthRepository;
import com.cotech.expensetracking.model.UserAuth;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    private final UserAuthRepository userAuthRepository;
    private final ModelMapper modelMapper;

    public UserAuthService(final UserAuthRepository userAuthRepository,
                           final ModelMapper mapper) {
        this.userAuthRepository = userAuthRepository;
        this.modelMapper = mapper;
    }

    public ResponseEntity<String> createUser(final UserAuth userAuth) {
        UserAuthEntity entity = this.userAuthRepository
                .findByEmail(userAuth.getEmail())
                .orElse(null);
        if (entity != null) {
            return new ResponseEntity<>("Email Already Exist!", HttpStatus.BAD_REQUEST);
        }
        String hashedPassword = BCrypt.hashpw(userAuth.getPassword(), BCrypt.gensalt(12));
        userAuth.setPassword(hashedPassword);
        entity = this.modelMapper.map(userAuth, UserAuthEntity.class);
        this.userAuthRepository.save(entity);
        return new ResponseEntity<>("Registration Successful!", HttpStatus.CREATED);
    }

    public ResponseEntity<String> loginUser(final UserAuth userAuth) {
        UserAuthEntity entity = this.userAuthRepository
                .findByEmail(userAuth.getEmail())
                .orElse(null);
        if (entity == null) {
            return new ResponseEntity<>("User Not Registered", HttpStatus.BAD_REQUEST);
        }
        boolean passwordMatch = BCrypt.checkpw(userAuth.getPassword(), entity.getPassword());
        if (passwordMatch) {
            return new ResponseEntity<>("Login Sucessful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Incorrect Password", HttpStatus.BAD_REQUEST);
    }
}
