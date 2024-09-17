package com.learning.journalApp.controller;

import com.learning.journalApp.entity.JournalEntry;
import com.learning.journalApp.entity.User;
import com.learning.journalApp.service.JournalEntryService;
import com.learning.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        if(user == null) {
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_GATEWAY);
        }
        List<JournalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>("No entries found for this user !", HttpStatus.OK);
    }

    @PostMapping("{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName) {
        return journalEntryService.saveEntry(userName, myEntry);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
        return journalEntryService.findById(myId);
    }

    @DeleteMapping("id/{userName}/{myId}")
    public boolean deleteJournalEntryById(@PathVariable String userName, @PathVariable ObjectId myId) {
        return journalEntryService.deleteById(myId, userName);
    }

    @PutMapping("id/{myId}")
    public boolean updateJournalEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry) {
        return journalEntryService.updateEntry(myId, myEntry);
    }
}
