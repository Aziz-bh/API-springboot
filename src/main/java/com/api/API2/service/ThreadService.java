package com.api.API2.service;

import com.api.API2.entity.Thread;
import com.api.API2.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThreadService {
    private ThreadRepository threadRepository;


    @Autowired
    public ThreadService(ThreadRepository threadRepository){
        this.threadRepository=threadRepository;
    }

    public Thread saveThread(Thread t) {
         return threadRepository.save(t);
    }
    public List<Thread> getAllThreads(){
        return threadRepository.findAll();
    }
    public Optional<Thread> getThreadById(String id){
        return threadRepository.findById(id);
    }
    public void deleteThread(String id){
        threadRepository.deleteById(id);
    }

}
