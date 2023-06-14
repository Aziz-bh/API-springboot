package com.api.API2.service;

import com.api.API2.entity.User;
import com.api.API2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
