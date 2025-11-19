package com.bitsa.api.service.impl;

import com.bitsa.api.dto.AuthRequest;
import com.bitsa.api.dto.AuthResponse;
import com.bitsa.api.model.User;
import com.bitsa.api.repository.UserRepository;
import com.bitsa.api.security.JwtUtil;
import com.bitsa.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) user.setRole(com.bitsa.api.model.Role.STUDENT);
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(Long id, User updates) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (updates.getName() != null) user.setName(updates.getName());
        if (updates.getRole() != null) user.setRole(updates.getRole());
        if (updates.getAvatarUrl() != null) user.setAvatarUrl(updates.getAvatarUrl());
        // Never update email or password from this endpoint for security
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
