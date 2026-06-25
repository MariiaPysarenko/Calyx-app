package com.calyx.service.impl;

import com.calyx.dto.request.CreateUserRequest;
import com.calyx.dto.response.UserResponse;
import com.calyx.exception.ValidationException;
import com.calyx.model.User;
import com.calyx.testutil.TestData;
import com.calyx.repository.UserRepository;
import com.calyx.util.PasswordHasher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void createUser_hashesPasswordAndSaves() {
        when(userRepository.findByEmail("new@calyx.local")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(5L);
            return user;
        });

        UserResponse response = userService.createUser(new CreateUserRequest(
                "New User", "new@calyx.local", "secret", 25, 70.0, 175, "maintain"
        ));

        assertEquals(Long.valueOf(5L), response.id());
        assertEquals("new@calyx.local", response.email());
    }

    @Test
    public void createUser_rejectsDuplicateEmail() {
        when(userRepository.findByEmail("maria@calyx.local"))
                .thenReturn(Optional.of(TestData.sampleUser()));

        assertThrows(ValidationException.class, () -> userService.createUser(new CreateUserRequest(
                "Maria", "maria@calyx.local", "secret", 25, 70.0, 175, "maintain"
        )));
    }

    @Test
    public void createUser_storesHashedPassword() {
        when(userRepository.findByEmail("hash@calyx.local")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            assertTrue(PasswordHasher.verify("plain", user.getPassword()));
            user.setId(1L);
            return user;
        });

        userService.createUser(new CreateUserRequest(
                "User", "hash@calyx.local", "plain", 25, 70.0, 175, "maintain"
        ));
    }
}
