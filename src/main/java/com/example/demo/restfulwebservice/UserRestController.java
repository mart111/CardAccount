package com.example.demo.restfulwebservice;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    @Autowired
    public UserRestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestParam("username") String username,
                                        @RequestParam("password") String password) {
        LOGGER.info("Trying to log in...");
        String token = userService.login(username, password);
        LOGGER.info("User logged in successfully! Token returned.");
        return ResponseEntity.ok(token);
    }

    @PostMapping("/api/sign-up")
    public ResponseEntity<String> signUp(@ModelAttribute("user") User user) {
        LOGGER.info("Trying to register new user...");
        String message = userService.signUp(user);
        LOGGER.info("User registered successfully!");
        return ResponseEntity.ok(message);
    }
}
