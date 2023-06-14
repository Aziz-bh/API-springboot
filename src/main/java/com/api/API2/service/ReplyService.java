package com.api.API2.service;

import com.api.API2.entity.Reply;
import com.api.API2.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {
    private ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository){
        this.replyRepository=replyRepository;
    }
    public Reply saveReply(Reply t) {
        return replyRepository.save(t);
    }
    public List<Reply> getAllReplies(){
        return replyRepository.findAll();
    }
    public Optional<Reply> getReplyById(String id){
        return replyRepository.findById(id);
    }
    public void deleteReply(String id){
        replyRepository.deleteById(id);
    }

}
