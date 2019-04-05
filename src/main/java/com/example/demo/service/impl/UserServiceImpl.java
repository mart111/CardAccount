package com.example.demo.service.impl;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.security.provider.JwtTokenProvider;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserRepo repo;

    private final JwtTokenProvider tokenProvider;

    private final RoleRepo roleRepo;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserRepo repo, JwtTokenProvider tokenProvider, RoleRepo roleRepo) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.repo = repo;
        this.tokenProvider = tokenProvider;
        this.roleRepo = roleRepo;
    }

    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return tokenProvider.createToken(username, Collections.singletonList(repo.findByUsername(username).getRole()));
        } catch (AuthenticationException e) {
            System.out.println(username);
            throw new RuntimeException("Invalid Credentials: " + e.getMessage());
        }
    }

    @Override
    public String signUp(User user) {
        try {
            Role role = new Role("USER");
            roleRepo.save(role);
            user.setRole(role);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repo.save(user);
            return "SAVED";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Saving failed");
        }
    }
}
