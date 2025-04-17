package com.example.api.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.api.entity.UserEntry;
import com.example.api.repository.UserEntryRepository;

@Component
public class UserEntryService {
    @Autowired
    private UserEntryRepository userEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(UserEntry userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles((Arrays.asList("User")));
        userEntryRepository.save(userEntry);
    }

    public void saveUser(UserEntry userEntry){
        userEntryRepository.save(userEntry);
    }
    public void saveAdmin(UserEntry userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles((Arrays.asList("User","ADMIN")));
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
