package com.api.API2.service;

import com.api.API2.entity.User;
import com.api.API2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private RestTemplate restTemplate;
    @Autowired
    public UserService(UserRepository userRepository,RestTemplate restTemplate){
        this.userRepository=userRepository;
        this.restTemplate = restTemplate;

    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserById(String id) {
        String url = "http://localhost:3000/test";
        try {
            ResponseEntity<User> responseEntity = restTemplate.getForEntity(url, User.class);
            System.out.println(responseEntity);
            System.out.println(responseEntity.getBody());
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else if (responseEntity.getStatusCode().is4xxClientError()) {
                return null;
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        }
        return null;
    }



}
