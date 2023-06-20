package com.api.API2.service;

import com.api.API2.entity.User;
import com.api.API2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
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


            // Set request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + id);

            // Create request entity
            RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(url));

            // Send request
            ResponseEntity<User> responseEntity = restTemplate.exchange(requestEntity, User.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else if (responseEntity.getStatusCode().is4xxClientError()) {
                if (responseEntity.getStatusCode().value() == 401) {
                    // Unauthorized access, return null
                    return null;
                }
            }
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        } catch (HttpClientErrorException ex) {
            return null;
        }
        return null;
    }



}
