package com.api.API2.service;

import com.api.API2.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ReplyService {
    private ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository){
        this.replyRepository=replyRepository;
    }

}
