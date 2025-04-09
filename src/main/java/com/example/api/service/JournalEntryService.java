package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.api.entity.JournalEntry;
import com.example.api.entity.UserEntry;
import com.example.api.repository.JournalEntryRepository;
@Component
public class JournalEntryService {

    
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserEntryService userEntryService;

    public void saveEntry(JournalEntry journalEntry, String userName){
        UserEntry user = userEntryService.findByUserName(userName);
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userEntryService.saveEntry(user);
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getByID(ObjectId Id){
        return journalEntryRepository.findById(Id);
    }

    public void deleteByID(ObjectId id, String userName){
    
        UserEntry user = userEntryService.findByUserName(userName);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userEntryService.saveEntry(user);
        journalEntryRepository.deleteById(id);
        
    }
}
