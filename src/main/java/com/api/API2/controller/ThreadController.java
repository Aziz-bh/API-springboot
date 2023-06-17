package com.api.API2.controller;


import com.api.API2.entity.Thread;
import com.api.API2.entity.User;
import com.api.API2.service.ThreadService;
import com.api.API2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public List<Thread> getAllThreads(HttpServletRequest request){

        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null){
            List<Thread> threads = threadService.getAllThreads();
            return threads;
        }
   else return null;

    }

    @GetMapping("/thread/{id}")
    public Optional<Thread> getAllThreadsp(@PathVariable String id,HttpServletRequest request){



        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null){

            return threadService.getThreadById(id);
        }
        else return null;
    }

    @PostMapping("/thread")
    public Thread saveThread(@RequestBody Thread t,HttpServletRequest request){
        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null){
            t.setUser(u);
            System.out.println(t);
        return threadService.saveThread(t);}
        else  return null;
    }
    @PutMapping("/thread/{id}")
    public void updateThread(@PathVariable String id,@RequestBody Thread t,HttpServletRequest request){
        User u=userService.getUserById(request.getHeader("Authorization").substring(7));

        if(u!=null){
            Optional<Thread> thread = threadService.getThreadById(id);
            Thread existingThread = thread.get();
            if(existingThread.getUser().getId().equals(u.getId())) {
                existingThread.setIssue(t.getIssue());
                existingThread.setTitle(t.getTitle());
                threadService.saveThread(existingThread);
            }
        }

    }

    @DeleteMapping("/thread/{id}")
    public void deleteThread(@PathVariable String id,HttpServletRequest request){
        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null) {
          if(  threadService.getThreadById(id).get().getUser().getId().equals(u.getId()))
            threadService.deleteThread(id);
        }
    }

}
