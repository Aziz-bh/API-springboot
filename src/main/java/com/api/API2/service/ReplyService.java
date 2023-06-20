package com.api.API2.service;

import com.api.API2.entity.Reply;
import com.api.API2.entity.Thread;
import com.api.API2.exceptions.ReplyException;
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
        throw  new ReplyException("saving Error");
    }
    public List<Reply> getAllReplies(){
        return replyRepository.findAll();
    }
    public Optional<Reply> getReplyById(String id){
        Optional<Reply> reply=replyRepository.findById(id);
        if(!reply.isEmpty())
        return replyRepository.findById(id);
        else throw  new ReplyException("not found");
    }
    public List<Reply> getRepliesByUser(String id){
        List<Reply> replies=replyRepository.findRepliesByUserId(id);
        if(replies!=null)
        return replies;
        else throw  new ReplyException("not found");
    }
    public void deleteReply(String id, String idUser) {
        Optional<Reply> reply = getReplyById(id);
        if (reply != null && reply.get().getUser().getId().equals(idUser)) {
            replyRepository.deleteById(id);
        }
        else throw  new ReplyException("not found");
    }

    public List<Reply> getRepliesByThread(String thread){
        return replyRepository.findRepliesByThreadId(thread);
    }

}
