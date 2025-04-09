package com.example.api.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entity.UserEntry;
import com.example.api.service.UserEntryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserEntryService userEntryService;
    @GetMapping("/")
    public List<UserEntry> getAllUser(){
        return userEntryService.getAll();
    }

    @PostMapping("/")
    public void createUser(@RequestBody UserEntry user) {
        userEntryService.saveEntry(user);
    }

    @GetMapping("/id/{myId}")
    public Optional<UserEntry> getUserById(@PathVariable ObjectId myId){
        return userEntryService.getByID(myId);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntry userEntry, @PathVariable String userName){
        UserEntry userInDb=userEntryService.findByUserName(userName);
        if(userInDb!=null){
            userInDb.setUserName(userEntry.getUserName());
            userInDb.setPassword(userEntry.getPassword());
            userEntryService.saveEntry(userInDb);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping("/id/{myId}")
    public boolean deleteUser(@PathVariable ObjectId myId){
        userEntryService.deleteByID(myId);
        return true;
    }
}
