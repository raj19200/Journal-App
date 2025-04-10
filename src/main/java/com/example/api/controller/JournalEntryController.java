package com.example.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entity.JournalEntry;
import com.example.api.entity.UserEntry;
import com.example.api.service.JournalEntryService;
import com.example.api.service.UserEntryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    
    
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserEntryService userEntryService;


    
    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        UserEntry user = userEntryService.findByUserName(userName);
       List<JournalEntry> all = user.getJournalEntries();
       if(all != null && !all.isEmpty()){
        return new ResponseEntity<>(all,HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName) {
        try{
            myEntry.setDate((LocalDateTime.now()));
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.getByID(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName){
        journalEntryService.deleteByID(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry, @PathVariable String userName) {
        JournalEntry old = journalEntryService.getByID(myId).orElse(null);
        
            if (old != null) {
                if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                    old.setTitle(newEntry.getTitle());
                }
                if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                    old.setContent(newEntry.getContent());
                }
        
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
                
            }
    
    
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
