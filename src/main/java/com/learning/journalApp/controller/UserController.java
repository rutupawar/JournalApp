package com.learning.journalApp.controller;

import com.learning.journalApp.entity.User;
import com.learning.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(){
        // This will be reached here, only if login is valid, through spring security + UserDetailsServiceImpl

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return new ResponseEntity<>("Welcome " + authentication.getName(), HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userInDb = userService.findByUserName(userName);
        if(userInDb != null) {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user);
            userService.saveEntry(userInDb);
            return new ResponseEntity<>("User updated", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("User not found, not updated", HttpStatus.NO_CONTENT);
    }
}
