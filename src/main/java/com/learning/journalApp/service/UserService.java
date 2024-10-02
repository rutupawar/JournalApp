package com.learning.journalApp.service;

import com.learning.journalApp.entity.User;
import com.learning.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean saveEntry(User user) {
        boolean status = false;
        try{
            userRepository.save(user);
            status = true;
        } catch (Exception e) {
            if(e instanceof DuplicateKeyException) {
                log.debug("Couldn't save User entry for {}, encountered error", user.getUserName(), e );
            }
            else{
                throw new RuntimeException(e);
            }
        }
        return status;
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

    public static String encode(String password) {
        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        return pwEncoder.encode(password);
    }
}
