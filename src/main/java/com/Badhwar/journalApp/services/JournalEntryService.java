package com.Badhwar.journalApp.services;

import com.Badhwar.journalApp.entity.JournalEntry;
import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry entry, String userName)
    {
        User user = userService.findByUserName(userName); //Extracted User
        entry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(entry); //Got the Saved Journal Entry
        user.getJournalEntries().add(saved); //Added the Journal Entry to specific User
        userService.saveUser(user); //Saving the user to update its Journal Entries List
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
