package com.nbrown.babyalbum.services;

import com.nbrown.babyalbum.model.KidUser;
import com.nbrown.babyalbum.protocol.KidUserDTO;
import com.nbrown.babyalbum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by avoiculet on 05/03/2019.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public KidUser create(String clientId, KidUserDTO user) {
        return userRepository.save(
                KidUser.builder()
                        .clientId(clientId)
                        .dateOfBirth(user.getDateOfBirth())
                        .name(user.getName())
                        .gender(user.getGender())
                        .build());
    }

    public KidUser get(String clientId) {
        return userRepository.findByClientId(clientId).get();
    }
}
