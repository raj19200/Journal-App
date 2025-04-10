package com.example.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.api.entity.UserEntry;
import com.example.api.repository.UserEntryRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserEntryRepository userEntryRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        UserEntry user = userEntryRepository.findByUserName(userName);
        if(user!=null){
            return User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
            
            
        }
        throw new UsernameNotFoundException("User not found with usename: "+ userName );
    }
}
