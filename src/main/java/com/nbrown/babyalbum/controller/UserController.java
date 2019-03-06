package com.nbrown.babyalbum.controller;

import com.nbrown.babyalbum.protocol.KidUserDTO;
import com.nbrown.babyalbum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.nbrown.babyalbum.util.Constants.CLIENT_ID;

/**
 * Created by avoiculet on 05/03/2019.
 */
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public KidUserDTO create(@RequestHeader(value = CLIENT_ID) String clientId, @RequestBody KidUserDTO userDTO) {
        return KidUserDTO.from(userService.create(clientId, userDTO));
    }

    @GetMapping("/user")
    public KidUserDTO get(@RequestHeader(value = CLIENT_ID) String clientId) {
        return KidUserDTO.from(userService.get(clientId));
    }
}
