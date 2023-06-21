package com.api.API2.controller;


import com.api.API2.entity.Thread;
import com.api.API2.entity.User;
import com.api.API2.service.ThreadService;
import com.api.API2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ThreadController {

    private UserService userService;
    private ThreadService threadService;
    @Autowired
    public ThreadController(ThreadService threadService,UserService userService){
        this.userService=userService;
        this.threadService=threadService;
    }
    @GetMapping("/threads")
    public ResponseEntity<Object> getAllThreads(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null){
            List<Thread> threads = threadService.getAllThreads();
            return ResponseEntity.ok(threads);
        }
    return ResponseEntity.notFound().build();

    }

    @GetMapping("/thread/{id}")
    public ResponseEntity<Object> getAllThreadsp(@PathVariable String id,HttpServletRequest request){


        // Here is where I extract the sent ID. If the ID contains a JWT token, we will use the following code to separate the JWT from the thread ID.
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            if(id.toLowerCase().contains(":")){
                String[] parts = id.split(":");
                String jwt = parts[0].trim();
                id = parts[1].trim();
                User user=userService.getUserById(jwt);
                if(user!=null){

                    return ResponseEntity.ok(threadService.getThreadById(id));
                }
            }
        }else {
            User u = userService.getUserById(request.getHeader("Authorization").substring(7));
            if (u != null) {

                return ResponseEntity.ok(threadService.getThreadById(id));
            }
        }

         return ResponseEntity.notFound().build();
    }

    @PostMapping("/thread")
    public ResponseEntity<Object> saveThread(@Valid @RequestBody Thread t,HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null){
            t.setUser(u);
            ResponseEntity.ok(threadService.saveThread(t));}
         return ResponseEntity.notFound().build();
    }
    @PutMapping("/thread/{id}")
    public ResponseEntity<Object> updateThread(@PathVariable String id, @Valid @RequestBody Thread t, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.getUserById(request.getHeader("Authorization").substring(7));

        if (user != null) {
            Optional<Thread> thread = threadService.getThreadById(id);
            if (thread.isPresent()) {
                Thread existingThread = thread.get();
                if (existingThread.getUser().getId().equals(user.getId())) {
                    existingThread.setIssue(t.getIssue());
                    existingThread.setTitle(t.getTitle());
                    threadService.saveThread(existingThread);
                    return ResponseEntity.ok().build();
                }
            }
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/thread/{id}")
    public ResponseEntity<Object> deleteThread(@PathVariable String id,HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null) {
          if(  threadService.getThreadById(id).get().getUser().getId().equals(u.getId()))
            threadService.deleteThread(id);
          return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
