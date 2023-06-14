package com.api.API2.controller;


import com.api.API2.entity.Thread;
import com.api.API2.entity.User;
import com.api.API2.service.ThreadService;
import com.api.API2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ThreadController {

    private ThreadService threadService;
    @Autowired
    public ThreadController(ThreadService threadService){
        this.threadService=threadService;
    }
    @GetMapping("/threads")
    public List<Thread> getAllThreads(){
        List<Thread> threads = threadService.getAllThreads();
        return threads;
    }

    @GetMapping("/threadsp")
    public Thread getAllThreadsp(){
        return threadService.saveThread(new Thread("ttitle"));
    }

}
