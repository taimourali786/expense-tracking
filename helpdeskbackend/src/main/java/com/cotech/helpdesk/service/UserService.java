package com.cotech.helpdesk.service;

import com.cotech.helpdesk.jpa.user.UserEntity;
import com.cotech.helpdesk.jpa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserEntity findUserById(final Integer id){
        return this.userRepository.findById(id).orElse(null);
    }
}
