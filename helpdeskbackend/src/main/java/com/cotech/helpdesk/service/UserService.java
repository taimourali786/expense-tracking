package com.cotech.helpdesk.service;

import com.cotech.helpdesk.jpa.user.UserEntity;
import com.cotech.helpdesk.jpa.user.UserRepository;
import com.cotech.helpdesk.model.ConvertableToEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements ConvertableToEntity<Integer, UserEntity> {
    private final UserRepository userRepository;

    @Override
    public UserEntity findEntityById(Integer id) {
        if (id == null) {
            return null;
        }
        return this.userRepository.findById(id).orElse(null);
    }
}
