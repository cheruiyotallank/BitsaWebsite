package com.bitsa.api.service;

import com.bitsa.api.dto.AuthRequest;
import com.bitsa.api.dto.AuthResponse;
import com.bitsa.api.model.User;

import java.util.List;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    User register(User user);
    User getCurrentUser(String email);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(Long id, User updates);
    void deleteUser(Long id);
}
