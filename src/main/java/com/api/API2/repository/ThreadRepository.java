package com.api.API2.repository;

import com.api.API2.entity.Thread;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThreadRepository extends MongoRepository<Thread,String> {

}
