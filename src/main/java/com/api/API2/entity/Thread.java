package com.api.API2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Document
public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Size(min=2)
    String title;
    @Size(min=2)
    String issue;
    int likes;


    @DBRef
    User user;

    @DBRef(lazy = true)
    private List<Reply> replies;
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public List<Reply> getReplies() {
        return replies;
    }

    public void addReply(Reply reply) {
        if (replies == null) {
            replies = new ArrayList<>();
        }
        replies.add(reply);
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public Thread(String title, String issue, int likes, User user) {
        this.title = title;
        this.issue = issue;
        this.likes = likes;
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Thread(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Thread() {
    }

    public Thread(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Thread{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", issue='" + issue + '\'' +
                ", likes=" + likes +
                ", user=" + user +
                ", replies=" + replies +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
