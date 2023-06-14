package com.api.API2.repository;

import com.api.API2.entity.Reply;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReplyRepository extends MongoRepository<Reply,String> {
}
