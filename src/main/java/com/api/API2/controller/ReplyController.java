package com.api.API2.controller;

import com.api.API2.entity.Reply;
import com.api.API2.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReplyController {
    private ReplyService replyService;
    @Autowired
    public ReplyController(ReplyService replyService){
        this.replyService=replyService;
    }

    @GetMapping("/Replies")
    public List<Reply> getReplies(){
        return
                replyService.getAllReplies();
    }
    @PostMapping("/reply")
    public Reply postReply(@RequestBody Reply r){
        System.out.println(r);
        return replyService.saveReply(r);
    }

    @GetMapping("/reply/{id}")
    public Optional<Reply> getReplyById(@PathVariable String id){
        return replyService.getReplyById(id);
    }

    @DeleteMapping("/reply/{id}")
    public void deleteReply(@PathVariable String id){
        replyService.deleteReply(id);
    }


}
