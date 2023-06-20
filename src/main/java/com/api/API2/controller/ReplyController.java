package com.api.API2.controller;
import com.api.API2.entity.Reply;
import com.api.API2.entity.User;
import com.api.API2.service.ReplyService;
import com.api.API2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null) {

           return ResponseEntity.ok(replyService.getRepliesByUser(u.getId()));
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/reply/{th}")
    public ResponseEntity<EntityModel<Reply>> postReply(@Valid @RequestBody Reply r, HttpServletRequest request, @PathVariable String th){

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User u=userService.getUserById(request.getHeader("Authorization").substring(7));
        if(u!=null) {
            r.setUser(u);
            Reply savedReply = replyService.saveReply(r, th);
            String jwt = request.getHeader("Authorization").substring(7);
            EntityModel<Reply> resource = EntityModel.of(r);
            String concatThJwt=jwt+":"+savedReply.getThread().getId();
            WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ThreadController.class).getAllThreadsp(concatThJwt, request));
            resource.add(linkBuilder.withRel("thread"));
            return ResponseEntity.created(URI.create("/reply/" + savedReply.getId())).body(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/reply/{id}")
    public ResponseEntity<EntityModel<Optional<Reply>>> getReplyById(@PathVariable String id, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Reply> r = replyService.getReplyById(id);
        String jwt = request.getHeader("Authorization").substring(7);
        EntityModel<Optional<Reply>> resource = EntityModel.of(r);
        String concatThJwt=jwt+":"+r.get().getThread().getId();
        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ThreadController.class).getAllThreadsp(concatThJwt, request));
        resource.add(linkBuilder.withRel("thread"));

        return ResponseEntity.ok(resource);
    }



    @DeleteMapping("/reply/{id}")
    public ResponseEntity<Object> deleteReply(@PathVariable String id, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.getUserById(request.getHeader("Authorization").substring(7));
        if (user != null) {
            replyService.deleteReply(id, user.getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



    @PutMapping("/reply/{id}")
    public ResponseEntity<Object> updateReply(@PathVariable String id, @RequestBody Reply r, HttpServletRequest request){
        Optional<Reply> replyOptional = replyService.getReplyById(id);
        if (replyOptional.isPresent()) {
            Reply existingReply = replyOptional.get();
            existingReply.setId(id); // Update the properties as needed
            existingReply.setAnswer(r.getAnswer());
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || authorizationHeader.isBlank()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String jwt = request.getHeader("Authorization").substring(7);
            EntityModel<Reply> resource = EntityModel.of(replyService.saveReply(existingReply,existingReply.getThread().getId()));
            String concatThJwt=jwt+":"+resource.getContent().getThread().getId();
            WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ThreadController.class).getAllThreadsp(concatThJwt, request));
            resource.add(linkBuilder.withRel("thread"));
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }


}
