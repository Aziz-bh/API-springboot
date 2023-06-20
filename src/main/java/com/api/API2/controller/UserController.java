package com.api.API2.controller;

import com.api.API2.entity.User;
import com.api.API2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }


    @GetMapping("/user")
    public User getAllUser(){

        //return userService.getAllUsers();
        return userService.getUserById("1999");
    }

    @PostMapping("/user")
    public User receiving(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7); // Remove "Bearer " prefix

            User u=userService.getUserById(jwt);

            return userService.getUserById(jwt);
        }
return null;

    }
}