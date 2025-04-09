package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.api.entity.UserEntry;
import com.example.api.repository.UserEntryRepository;

@Component
public class UserEntryService {
    @Autowired
    private UserEntryRepository userEntryRepository;

    public void saveEntry(UserEntry userEntry){
        userEntryRepository.save(userEntry);
    }

    public List<UserEntry> getAll(){
        return userEntryRepository.findAll();
    }

    public Optional<UserEntry> getByID(ObjectId Id){
        return userEntryRepository.findById(Id);
    }

    public void deleteByID(ObjectId id){
        userEntryRepository.deleteById(id);
    }

    public UserEntry findByUserName(String userName){
        return userEntryRepository.findByUserName(userName);
    }
}
