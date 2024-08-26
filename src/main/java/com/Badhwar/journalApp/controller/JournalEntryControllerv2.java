//package com.Badhwar.journalApp.controller;
//
//import com.Badhwar.journalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/_journal")
//public class JournalEntryControllerv2 {
//
//    private Map<Long, JournalEntry> journalEnteries = new HashMap<>();
//
//    @GetMapping
//    public List<JournalEntry> getAll() //localhost:8080/journal GET
//    {
//        return new ArrayList<>(journalEnteries.values());
//    }
//
//    @PostMapping
//    public boolean createEntry(@RequestBody JournalEntry entry) //localhost:8080/journal POST
//    {
//        journalEnteries.put((long)entry.getId(), entry);
//        return true;
//    }
//    //@RequestBody -> is to mention to Spring, that please take the Data from the request and turn it into Java Object.
//
//    //localhost:8080/id/2 -> Path Variable
//    //localhost:8080?id=2 -> Request Parameter
//    @GetMapping("id/{myId}")
//    public JournalEntry getJournalEntryById(@PathVariable Long myId)
//    {
//        return journalEnteries.get(myId);
//    }
//
//    @DeleteMapping("id/{myId}")
//    public JournalEntry deleteJournalEntryById(@PathVariable Long myId)
//    {
//       return journalEnteries.remove(myId);
//    }
//
//    @PutMapping("id/{myId}")
//    public JournalEntry updateJournalEntryById(@PathVariable Long myId, @RequestBody JournalEntry entry)
//    {
//        return journalEnteries.put(myId, entry);
//    }
//}
