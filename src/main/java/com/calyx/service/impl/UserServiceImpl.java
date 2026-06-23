package com.calyx.service.impl;

import com.calyx.dto.request.CreateUserRequest;
import com.calyx.dto.response.UserResponse;
import com.calyx.exception.ValidationException;
import com.calyx.mapper.UserMapper;
import com.calyx.model.User;
import com.calyx.repository.UserRepository;
import com.calyx.service.UserService;
import com.calyx.util.PasswordHasher;
import com.calyx.util.Validator;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void validateRequest(CreateUserRequest request) {
        Validator.requireNonBlank(request.name(), "Name");
        Validator.requireValidEmail(request.email());
        Validator.requireNonBlank(request.password(), "Password");
        Validator.requirePositive(request.age(), "Age");
        Validator.requirePositive(request.weight(), "Weight");
        Validator.requirePositive(request.height(), "Height");
        Validator.requireNonBlank(request.goal(), "Goal");
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        validateRequest(request);

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ValidationException("Email already exists");
        }

        String hashedPassword = PasswordHasher.hash(request.password());
        User user = UserMapper.toEntity(request, hashedPassword);
        User saved = userRepository.save(user);

        return UserMapper.toResponse(saved);
    }

    @Override
    public UserResponse getUserById(Long id) {
        Validator.requireValidId(id, "user id");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}
