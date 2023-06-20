package com.api.API2.service;

import com.api.API2.entity.Reply;
import com.api.API2.entity.Thread;
import com.api.API2.repository.ReplyRepository;
import com.api.API2.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {
    private ReplyRepository replyRepository;
    private UserService userService;
    private ThreadService threadService;

    @Autowired
    public ReplyService(ReplyRepository replyRepository,UserService userService,ThreadService threadService){
        this.replyRepository=replyRepository;
        this.userService=userService;
        this.threadService=threadService;
    }
    public Reply saveReply(Reply t,String th) {
        if(threadService.getThreadById(th).get()!=null){
            Thread thread=threadService.getThreadById(th).get();
            System.out.println(thread);
        t.setThread(thread);
        Reply r=replyRepository.save(t);
            thread.addReply(t);
            threadService.saveThread(thread);
        return r;}
        return null;
    }
    public List<Reply> getAllReplies(){
        return replyRepository.findAll();
    }
    public Optional<Reply> getReplyById(String id){
        return replyRepository.findById(id);
    }
    public List<Reply> getRepliesByUser(String id){
        return replyRepository.findRepliesByUserId(id);
    }
    public void deleteReply(String id,String idUser){
        if(replyRepository.findRepliesByUserId(idUser).contains(getReplyById(id)))
        replyRepository.deleteById(id);
    }
    public List<Reply> getRepliesByThread(String thread){
        return replyRepository.findRepliesByThreadId(thread);
    }

}
