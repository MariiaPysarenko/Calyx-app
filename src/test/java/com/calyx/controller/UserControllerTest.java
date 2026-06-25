package com.calyx.controller;

import com.calyx.dto.request.CreateUserRequest;
import com.calyx.dto.response.UserResponse;
import com.calyx.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;

    @Before
    public void setUp() {
        userController = new UserController(userService);
    }

    @Test
    public void create_delegatesToService() {
        CreateUserRequest request = new CreateUserRequest(
                "Maria", "maria@calyx.local", "pass", 28, 65.0, 168, "lose_weight"
        );
        UserResponse expected = new UserResponse(1L, "Maria", "maria@calyx.local", 28, 65.0, 168, "lose_weight");
        when(userService.createUser(request)).thenReturn(expected);

        UserResponse response = userController.create(request);

        assertEquals("Maria", response.name());
    }
}
