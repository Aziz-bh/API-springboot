package com.api.API2.service;

import com.api.API2.entity.Thread;
import com.api.API2.exceptions.ReplyException;
import com.api.API2.exceptions.ThreadException;
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
        List<Thread> threads = threadRepository.findAll();
        if(!threads.isEmpty())
        return threadRepository.findAll();
        else throw  new ThreadException("not found");
    }
    public Optional<Thread> getThreadById(String id){
        Optional<Thread> t= threadRepository.findById(id);
        if(!t.isEmpty())
        return t;
        else throw new ThreadException("not found");
    }
    public void deleteThread(String id){

        Optional<Thread> t= threadRepository.findById(id);
        if(t.isEmpty())
            throw new ThreadException("not found");
        threadRepository.deleteById(id);
    }

}
