package com.learning.journalApp.config;

import com.learning.journalApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        try {
            // Can we use securityMatcher here ?
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authorize -> authorize.requestMatchers("/journal/**", "/user/**").authenticated()
                            /*
                            * When we say, anyRequest.permitall, we have to make sure http request header should be of type non-auth.
                            * If its type Basic auth, spring boot will still secure auhorization.
                            */
                            .anyRequest().permitAll() )
                    .httpBasic(Customizer.withDefaults())    // This is required to enable basic authentication. Else it will not use UserDetailsService for authentication
            ;
            return http.build();
        } catch (Exception e) {
            throw e;
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
