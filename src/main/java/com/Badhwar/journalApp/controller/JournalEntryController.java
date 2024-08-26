package com.Badhwar.journalApp.controller;

import com.Badhwar.journalApp.JournalApplication;
import com.Badhwar.journalApp.entity.JournalEntry;
import com.Badhwar.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll() //localhost:8080/journal GET
    {
        return journalEntryService.getAllEntries();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry entry) //localhost:8080/journal POST
    {
        entry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(entry);
        return entry;
    }
    //@RequestBody -> is to mention to Spring, that please take the Data from the request and turn it into Java Object.

    //localhost:8080/id/2 -> Path Variable
    //localhost:8080?id=2 -> Request Parameter
    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId) {
        return journalEntryService.findById(myId).orElse(null);
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myId) {
        journalEntryService.deleteById(myId);
        return true;
    }

    @PutMapping("id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        JournalEntry prevEntry = journalEntryService.findById(myId).orElse(null);
        if(prevEntry != null)
        {
            prevEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")? newEntry.getTitle() : prevEntry.getTitle());
            prevEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")? newEntry.getContent() : prevEntry.getContent());
        }
        journalEntryService.saveEntry(prevEntry);
        return prevEntry;
    }
}
