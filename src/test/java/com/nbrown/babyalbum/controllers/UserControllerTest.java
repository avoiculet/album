package com.nbrown.babyalbum.controllers;

import com.nbrown.babyalbum.model.KidUser;
import com.nbrown.babyalbum.protocol.KidUserDTO;
import com.nbrown.babyalbum.services.UserService;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static com.nbrown.babyalbum.model.Gender.MALE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by avoiculet on 05/03/2019.
 */
public class UserControllerTest {
    private static String TEST_CLIENT_ID = "abcd";
    private UserController userController;
    private KidUserDTO testUserDTO;
    private UserService userService;
    private KidUser testUser;

    @Before
    public void setUp() {
        LocalDate dateOfBirth = LocalDate.now();
        this.testUserDTO = new KidUserDTO("testUser", dateOfBirth, MALE);
        this.testUser = new KidUser("testUser", dateOfBirth, MALE);
        this.userService = mock(UserService.class);
        when(userService.create(TEST_CLIENT_ID, testUserDTO)).thenReturn(testUser);
        when(userService.get(TEST_CLIENT_ID)).thenReturn(testUser);
        this.userController = new UserController(userService);
    }
    @Test
    public void shouldSetUserData() {
        KidUserDTO user = userController.create(TEST_CLIENT_ID, testUserDTO);
        verify(userService).create(TEST_CLIENT_ID, testUserDTO);
        assertThat(user,is(testUserDTO));
    }

    @Test
    public void shouldGetUserByClientId() {
        KidUserDTO user = userController.get(TEST_CLIENT_ID);
        verify(userService).get(TEST_CLIENT_ID);
        assertThat(user, is(testUserDTO));
    }

}
