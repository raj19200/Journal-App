package com.example.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entity.UserEntry;
import com.example.api.service.UserEntryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserEntryService userEntryService;
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllusers(){
        List<UserEntry> all = userEntryService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody UserEntry user) {
        userEntryService.saveAdmin(user);
    }
    
}
