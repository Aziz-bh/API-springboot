package com.api.API2.controller;

import com.api.API2.entity.Reply;
import com.api.API2.entity.User;
import com.api.API2.service.ReplyService;
import com.api.API2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
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
        public ResponseEntity<List<Reply>> getReplies(HttpServletRequest request){

        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null) {

           return ResponseEntity.ok(replyService.getRepliesByUser(u.getId()));
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/reply/{th}")
    public  ResponseEntity<Reply> postReply(@RequestBody Reply r,HttpServletRequest request,@PathVariable String th){
        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null) {
            r.setUser(u);
            Reply savedReply = replyService.saveReply(r, th);
            return ResponseEntity.created(URI.create("/reply/" + savedReply.getId())).body(savedReply);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/reply/{id}")
    public ResponseEntity<Optional<Reply>> getReplyById(@PathVariable String id,HttpServletRequest request){
        return ResponseEntity.ok(replyService.getReplyById(id));
    }



    @DeleteMapping("/reply/{id}")
    public ResponseEntity<Object> deleteReply(@PathVariable String id, HttpServletRequest request) {
        User user = userService.getUserById(request.getHeader("Authorization").substring(7));
        if (user != null) {
            replyService.deleteReply(id, user.getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



    @PutMapping("/reply/{id}")
    public ResponseEntity<Object> updateReply(@PathVariable String id,@RequestBody Reply r,HttpServletRequest request){
        Optional<Reply> replyOptional = replyService.getReplyById(id);
        if (replyOptional.isPresent()) {
            Reply existingReply = replyOptional.get();
            existingReply.setId(id); // Update the properties as needed
            existingReply.setAnswer(r.getAnswer());
            return ResponseEntity.ok(replyService.saveReply(existingReply,existingReply.getThread().getId()));
        }
        return ResponseEntity.notFound().build();
    }


}
