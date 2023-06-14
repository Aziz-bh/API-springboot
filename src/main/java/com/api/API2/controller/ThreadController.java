package com.api.API2.controller;


import com.api.API2.entity.Reply;
import com.api.API2.entity.Thread;
import com.api.API2.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/thread/{id}")
    public Optional<Thread> getAllThreadsp(@PathVariable String id){
        return threadService.getThreadById(id);
    }

    @PostMapping("/thread")
    public Thread saveThread(@RequestBody Thread t){
        return threadService.saveThread(t);
    }
    @PutMapping("/thread/{id}")
    public void updateThread(@PathVariable String id,@RequestBody Thread t){
        Optional<Thread> thread = threadService.getThreadById(id);
        Thread existingThread = thread.get();
        existingThread.setIssue(t.getIssue());
        existingThread.setTitle(t.getTitle());
        threadService.saveThread(existingThread);
    }

    @DeleteMapping("/thread/{id}")
    public void deleteThread(@PathVariable String id){
        threadService.deleteThread(id);
    }

}
