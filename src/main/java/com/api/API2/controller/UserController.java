package com.api.API2.controller;

import com.api.API2.entity.User;
import com.api.API2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }
    @GetMapping("/user")
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }

}
