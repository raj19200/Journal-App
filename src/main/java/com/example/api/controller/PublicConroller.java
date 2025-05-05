package com.example.api.controller;
import com.example.api.service.UserDetailsServiceImpl;
import com.example.api.service.UserEntryService;
import com.example.api.utility.Jwtutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private Jwtutil jwtutil;

    @PostMapping("signUp")
    public void SignUp(@RequestBody UserEntry user) {
        userEntryService.saveNewUser(user);
    }

    @PostMapping("login")
    public ResponseEntity<String> Login(@RequestBody UserEntry user) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtutil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}
