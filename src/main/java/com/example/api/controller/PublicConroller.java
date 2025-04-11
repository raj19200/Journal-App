package com.example.api.controller;
import com.example.api.service.UserEntryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entity.UserEntry;

@RestController
@RequestMapping("/public")
public class PublicConroller {

    @Autowired
    private UserEntryService userEntryService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody UserEntry user) {
        userEntryService.saveEntry(user);
    }
}
