package com.api.API2.repository;

import com.api.API2.entity.Reply;
import com.api.API2.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends MongoRepository<Reply,String> {
    List<Reply> findRepliesByUserId(String u);
    List<Reply> findRepliesByThreadId(String thread);
}
