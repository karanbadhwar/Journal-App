package com.Badhwar.journalApp.controller;

import com.Badhwar.journalApp.entity.JournalEntry;
import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.services.JournalEntryService;
import com.Badhwar.journalApp.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Tag(name="Jounral APIs", description = "Read, Update & Delete User")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get all journal enteries of a User") //Swagger Annotations for giving name to the request
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() //localhost:8080/journal GET
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if (allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) //localhost:8080/journal POST
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
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
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String myId) {
        ObjectId objectId = new ObjectId(myId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> list = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(objectId)).toList();
        if(!list.isEmpty())
        {
            Optional<JournalEntry> entry = journalEntryService.findById(objectId);
            if (entry.isPresent()) {
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            }
        }

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
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String myId) {
        ObjectId objectId = new ObjectId(myId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryService.deleteById(objectId, userName);
        if(removed)
        {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable String myId,
                                                               @RequestBody JournalEntry newEntry) {
        ObjectId objectId = new ObjectId(myId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> list = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(objectId)).toList();
       if(!list.isEmpty())
       {
           JournalEntry prevEntry = journalEntryService.findById(objectId).orElse(null);
           if (prevEntry != null) {
               prevEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : prevEntry.getTitle());
               prevEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : prevEntry.getContent());
               journalEntryService.saveEntry(prevEntry);
               return new ResponseEntity<>(prevEntry, HttpStatus.OK);
           }
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
