package com.example.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.apiResponse.WeatherResponse;
import com.example.api.entity.UserEntry;
import com.example.api.repository.UserEntryRepository;
import com.example.api.service.UserEntryService;
import com.example.api.service.WeatherService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    private UserEntryRepository userEntryRepository;

    @Autowired
    private WeatherService weatherService;
    // @GetMapping("/")
    // public List<UserEntry> getAllUser(){
    //     return userEntryService.getAll();
    // }

    // @PostMapping("/")
    // public void createUser(@RequestBody UserEntry user) {
    //     userEntryService.saveEntry(user);
    // }

    // @GetMapping("/id/{myId}")
    // public Optional<UserEntry> getUserById(@PathVariable ObjectId myId){
    //     return userEntryService.getByID(myId);
    // }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntry userEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry userInDb=userEntryService.findByUserName(userName);
        if(userInDb!=null){
            userInDb.setUserName(userEntry.getUserName());
            userInDb.setPassword(userEntry.getPassword());
            userEntryService.saveNewUser(userInDb);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userEntryRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
        WeatherResponse weatherResponse= weatherService.getWeather("windsor");
        String greetings="";
        if(weatherResponse!=null){
            greetings = " , Weather feels like "+ weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi..."+ authentication.getName() + greetings, HttpStatus.OK);
    }
    

}
