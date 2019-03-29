package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {

    String login(String username, String password);

    String signUp(User user);
}
