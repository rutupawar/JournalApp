package com.learning.journalApp.service;

import com.learning.journalApp.entity.JournalEntry;
import com.learning.journalApp.entity.User;
import com.learning.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Transactional
    public ResponseEntity<?> saveEntry(String userName, JournalEntry journalEntry) {
        User user = userService.findByUserName(userName);
        if(user == null) {
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_GATEWAY);
        }
        journalEntry.setDate(LocalDateTime.now());
        // In saved copy, we receive ID field for journalEntry
        JournalEntry savedCopy = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(savedCopy);
        userService.saveEntry(user);
//        userService.saveEntry(null);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public ResponseEntity<?> findById(ObjectId id) {
        Optional<JournalEntry> document = journalEntryRepository.findById(id.toString());
        return document.map((journalEntry) -> new ResponseEntity<>(journalEntry, HttpStatus.OK)).orElseGet(() -> null);
    }

    public boolean deleteById(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        if(user == null) {
            return false;
        }
        user.getJournalEntries().removeIf(var -> var.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id.toString());
        return true;
    }

    public boolean updateEntry(ObjectId myId, JournalEntry myEntry) {
        JournalEntry document = (JournalEntry) this.findById(myId).getBody();  // we get structure data in body. Fetching data from body is unnecessary
        boolean isUpdated = false;
        if (document != null) {
            String title = myEntry.getTitle();
            if (title != null && !title.isBlank()) {
                document.setTitle(title);
                isUpdated = true;
            }

            String content = myEntry.getContent();
            if (content != null && !content.isBlank()) {
                document.setContent(content);
                isUpdated = true;
            }
            journalEntryRepository.save(document);
        }
        return isUpdated;
    }
}
