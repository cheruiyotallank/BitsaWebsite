package com.bitsa.api.controller;

import com.bitsa.api.dto.AuthRequest;
import com.bitsa.api.dto.AuthResponse;
import com.bitsa.api.dto.DTOMapper;
import com.bitsa.api.dto.UserDTO;
import com.bitsa.api.model.User;
import com.bitsa.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private DTOMapper dtoMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest req) {
        AuthResponse resp = authService.login(req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid User user) {
        User created = authService.register(user);
        return ResponseEntity.ok(dtoMapper.toUserDTO(created));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> profile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return ResponseEntity.status(401).build();
        User u = authService.getCurrentUser(userDetails.getUsername());
        return ResponseEntity.ok(dtoMapper.toUserDTO(u));
    }
}
