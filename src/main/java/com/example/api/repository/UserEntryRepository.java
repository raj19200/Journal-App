package com.example.api.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.api.entity.UserEntry;

public interface UserEntryRepository extends MongoRepository<UserEntry,ObjectId> {
    UserEntry findByUserName(String userName);
}
