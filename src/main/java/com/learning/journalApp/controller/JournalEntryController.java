package com.learning.journalApp.controller;

import com.learning.journalApp.entity.JournalEntry;
import com.learning.journalApp.entity.User;
import com.learning.journalApp.service.JournalEntryService;
import com.learning.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

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

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return journalEntryService.saveEntry(userName, myEntry);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntry> jnl = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();

        if (jnl == null || jnl.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(jnl, HttpStatus.OK);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntry> jnl = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();

        if (jnl == null || jnl.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        jnl.forEach(entry -> journalEntryService.deleteById(myId, userName));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
