package com.calyx.controller;

import com.calyx.dto.request.CreateUserRequest;
import com.calyx.dto.response.UserResponse;
import com.calyx.service.UserService;

import java.util.List;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserResponse create(CreateUserRequest request) {
        return userService.createUser(request);
    }

    public UserResponse getById(Long id) {
        return userService.getUserById(id);
    }

    public List<UserResponse> getAll() {
        return userService.getAllUsers();
    }
}
