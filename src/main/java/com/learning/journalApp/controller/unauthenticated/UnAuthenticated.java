package com.learning.journalApp.controller.unauthenticated;

import com.learning.journalApp.entity.User;
import com.learning.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/public")
@RestController
public class UnAuthenticated {

    @Autowired
    private UserService userService;

    @GetMapping("health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if(userService.saveEntry(user)) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Error while saving new user entry. userName must be unique",HttpStatus.BAD_REQUEST);
    }
}
