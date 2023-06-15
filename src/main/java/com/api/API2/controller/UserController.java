package com.api.API2.controller;

import com.api.API2.entity.User;
import com.api.API2.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public User reciving(@RequestBody String id){
        System.out.println(id);
       return userService.getUserById(id);
    }
}