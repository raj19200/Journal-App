package com.example.api.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.entity.JournalEntry;
import com.example.api.entity.UserEntry;
import com.example.api.repository.JournalEntryRepository;
@Component
public class JournalEntryService {

    
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserEntryService userEntryService;
    
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) throws Exception{
        try {
            UserEntry user = userEntryService.findByUserName(userName);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userEntryService.saveUser(user);
        } catch (Exception e) {
            throw new Exception(e);
        }
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

    public boolean deleteByID(ObjectId id, String userName){
        boolean removed = false;
        try{
            UserEntry user = userEntryService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x->x.getId().equals(id));
            if(removed){
                userEntryService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        }catch(Exception e){
            throw new RuntimeException("An error occurred while deleting the entry",e);
        }
        return removed;
        
    }

    public List<JournalEntry> findByUserName(String userName){
    
    }
}
