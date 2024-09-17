package com.learning.journalApp.service;

import com.learning.journalApp.entity.User;
import com.learning.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean saveEntry(User user) {
        userRepository.save(user);
        return true;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public ResponseEntity<User> findById(ObjectId id) {
        Optional<User> document = userRepository.findById(id.toString());
        return document.map((user) -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public boolean deleteById(ObjectId id) {
        userRepository.deleteById(id.toString());
        return true;
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
