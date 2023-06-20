package com.api.API2.controller;

import com.api.API2.entity.Reply;
import com.api.API2.entity.User;
import com.api.API2.service.ReplyService;
import com.api.API2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
public class ReplyController {
    private ReplyService replyService;
    private UserService userService;
    @Autowired
    public ReplyController(ReplyService replyService,UserService userService){
        this.userService=userService;
        this.replyService=replyService;
    }

    @GetMapping("/Replies")
    public List<Reply> getReplies(HttpServletRequest request){
        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null) {

            return replyService.getRepliesByUser(u.getId());
        }
        else return null;
    }
    @PostMapping("/reply/{th}")
    public Reply postReply(@RequestBody Reply r,HttpServletRequest request,@PathVariable String th){
        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null) {
            r.setUser(u);
            return replyService.saveReply(r,th);
        }
        return null;
    }

    @GetMapping("/reply/{id}")
    public Optional<Reply> getReplyById(@PathVariable String id,HttpServletRequest request){
        return replyService.getReplyById(id);
    }



    @DeleteMapping("/reply/{id}")
    public void deleteReply(@PathVariable String id,HttpServletRequest request){
        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null) {
            replyService.deleteReply(id,u.getId());
        }
    }

    @PutMapping("/reply/{id}")
    public void updateReply(@PathVariable String id,@RequestBody Reply r,HttpServletRequest request){
        Optional<Reply> replyOptional = replyService.getReplyById(id);
        if (replyOptional.isPresent()) {
            Reply existingReply = replyOptional.get();
            existingReply.setId(id); // Update the properties as needed
            existingReply.setAnswer(r.getAnswer());
            replyService.saveReply(existingReply,existingReply.getThread().getId());
        }
    }


}
