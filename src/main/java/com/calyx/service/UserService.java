package com.calyx.service;

import com.calyx.dto.request.CreateUserRequest;
import com.calyx.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();
}
