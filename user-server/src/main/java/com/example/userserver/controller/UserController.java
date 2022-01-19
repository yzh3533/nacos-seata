package com.example.userserver.controller;

import com.example.userserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public void createOrder(Boolean flag){
        userService.updateAccount(new BigDecimal("20"),1L,flag);
    }
}
