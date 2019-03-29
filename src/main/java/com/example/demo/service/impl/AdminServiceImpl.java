package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final UserRepo userRepo;

    @Autowired
    public AdminServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.info("Trying to fetch all users from database...");
        List<User> users = userRepo.findAll(Sort.by(Sort.Order.asc("username")));
        LOGGER.info("Total users count: " + users.size());
        return users;
    }
}
