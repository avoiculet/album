package com.nbrown.babyalbum.services;

import com.nbrown.babyalbum.model.KidUser;
import com.nbrown.babyalbum.protocol.KidUserDTO;
import com.nbrown.babyalbum.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Optional;

import static com.nbrown.babyalbum.TestUtil.TEST_CLIENT_ID;
import static com.nbrown.babyalbum.TestUtil.TEST_GENDER;
import static com.nbrown.babyalbum.TestUtil.TEST_USERNAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by avoiculet on 05/03/2019.
 */
public class UserServiceTest {
    private static LocalDate DATE_OF_BIRTH = LocalDate.now();
    private UserService userService;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        this.userRepository = mock(UserRepository.class);
        KidUser kidUser = KidUser.builder()
                .clientId(TEST_CLIENT_ID)
                .dateOfBirth(DATE_OF_BIRTH)
                .gender(TEST_GENDER)
                .name(TEST_USERNAME)
                .build();
        when(userRepository.save(any(KidUser.class))).thenReturn(kidUser);
        when(userRepository.findByClientId(TEST_CLIENT_ID)).thenReturn(Optional.of(kidUser));
        this.userService = new UserService(userRepository);
    }

    @Test
    public void shouldSaveUser() {
        KidUser user = userService.create(TEST_CLIENT_ID, new KidUserDTO(TEST_USERNAME, LocalDate.now(), TEST_GENDER));
        verify(userRepository).save(any(KidUser.class));
        assertThat(user.getClientId(), is(TEST_CLIENT_ID));
        assertThat(user.getDateOfBirth(), is(DATE_OF_BIRTH));
        assertThat(user.getGender(), is(TEST_GENDER));
        assertThat(user.getName(), is(TEST_USERNAME));
    }

    @Test
    public void shouldGetUser() {
        KidUser user = userService.get(TEST_CLIENT_ID);
        verify(userRepository).findByClientId(TEST_CLIENT_ID);
        assertThat(user.getClientId(), is(TEST_CLIENT_ID));
        assertThat(user.getDateOfBirth(), is(DATE_OF_BIRTH));
        assertThat(user.getGender(), is(TEST_GENDER));
        assertThat(user.getName(), is(TEST_USERNAME));
    }
}
