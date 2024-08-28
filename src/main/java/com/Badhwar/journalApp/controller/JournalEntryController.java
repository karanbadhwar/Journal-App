package com.Badhwar.journalApp.controller;

import com.Badhwar.journalApp.entity.JournalEntry;
import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.services.JournalEntryService;
import com.Badhwar.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName) //localhost:8080/journal GET
    {
        User user = userService.findByUserName(userName);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if (allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry, @PathVariable String userName) //localhost:8080/journal POST
    {
        try {
            journalEntryService.saveEntry(entry, userName);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //@RequestBody -> is to mention to Spring, that please take the Data from the request and turn it into Java Object.

    //localhost:8080/id/2 -> Path Variable
    //localhost:8080?id=2 -> Request Parameter
    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> entry = journalEntryService.findById(myId);
//        return entry.map(journalEntry -> new ResponseEntity<>(journalEntry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        // Complete explanation below
//        return entry.map(new Function<JournalEntry, ResponseEntity<JournalEntry>>() {
//            @Override
//            public ResponseEntity<JournalEntry> apply(JournalEntry journalEntry) {
//                return new ResponseEntity<>(journalEntry, HttpStatus.OK);
//            }
//        }).orElseGet(new Supplier<ResponseEntity<JournalEntry>>() {
//            @Override
//            public ResponseEntity<JournalEntry> get() {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        });

        if (entry.isPresent()) {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName) {
        journalEntryService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId myId,
                                                               @RequestBody JournalEntry newEntry,
                                                               @PathVariable String userName
    ) {
        JournalEntry prevEntry = journalEntryService.findById(myId).orElse(null);
        if (prevEntry != null) {
            prevEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : prevEntry.getTitle());
            prevEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : prevEntry.getContent());
            journalEntryService.saveEntry(prevEntry);
            return new ResponseEntity<>(prevEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
