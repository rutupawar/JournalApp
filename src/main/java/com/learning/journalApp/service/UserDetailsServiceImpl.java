package com.learning.journalApp.service;

import com.learning.journalApp.entity.User;
import com.learning.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private String[] tempgetRoles (List<String> roles) {
        if (roles == null) {
            return new String[]{""};
        }
        return roles.toArray(new String[0]);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(tempgetRoles(user.getRoles()))
                    .build();
        }
        throw new UsernameNotFoundException("Username not found for this userName: " + username );
    }
}
