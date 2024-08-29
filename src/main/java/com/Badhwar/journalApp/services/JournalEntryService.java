package com.Badhwar.journalApp.services;

import com.Badhwar.journalApp.entity.JournalEntry;
import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    //Transactional annotation, mean that whatever is written inside the Method should complete, if something fails roll back everything
    @Transactional //This achieves Atomicity & Isolation
    public void saveEntry(JournalEntry entry, String userName)
    {
        try {
            User user = userService.findByUserName(userName); //Extracted User
            entry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(entry); //Got the Saved Journal Entry
            user.getJournalEntries().add(saved); //Added the Journal Entry to specific User
            userService.saveUser(user); //Saving the user to update its Journal Entries List
        } catch(Exception e)
        {
            System.out.println(e);
            throw new RuntimeException("An Error occurred while saving the entry. ", e);
        }

    }

    public void saveEntry(JournalEntry entry)
    {
     journalEntryRepository.save(entry);
    }

    public List<JournalEntry> getAllEntries()
    {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id)
    {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName)
    {
        User user = userService.findByUserName(userName); //Extracted User
        user.getJournalEntries().removeIf(jE -> jE.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }

}
