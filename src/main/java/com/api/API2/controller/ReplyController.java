package com.api.API2.controller;

import com.api.API2.entity.Reply;
import com.api.API2.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public List<Reply> getReplies(HttpServletRequest request){
        return
                replyService.getAllReplies();
    }
    @PostMapping("/reply")
    public Reply postReply(@RequestBody Reply r,HttpServletRequest request){
        System.out.println(r);
        return replyService.saveReply(r);
    }

    @GetMapping("/reply/{id}")
    public Optional<Reply> getReplyById(@PathVariable String id,HttpServletRequest request){
        return replyService.getReplyById(id);
    }

    @DeleteMapping("/reply/{id}")
    public void deleteReply(@PathVariable String id,HttpServletRequest request){
        replyService.deleteReply(id);
    }

    @PutMapping("/reply/{id}")
    public void updateReply(@PathVariable String id,@RequestBody Reply r,HttpServletRequest request){
        Optional<Reply> replyOptional = replyService.getReplyById(id);
        if (replyOptional.isPresent()) {
            Reply existingReply = replyOptional.get();
            existingReply.setId(id); // Update the properties as needed
            existingReply.setAnswer(r.getAnswer());
            replyService.saveReply(existingReply);
        }
    }


}
