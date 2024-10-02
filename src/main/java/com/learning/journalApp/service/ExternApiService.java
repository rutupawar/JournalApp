package com.learning.journalApp.service;

import com.learning.journalApp.entity.response.ExternResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ex")
public class ExternApiService {

    private final static String API = "https://api.restful-api.dev/objects";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping
    public ExternResponse addEntry(@RequestBody ExternResponse externResponse){

        HttpEntity<ExternResponse> request = new HttpEntity<ExternResponse>(externResponse);

        ResponseEntity<ExternResponse> exchange =
                restTemplate().postForEntity(API,request, ExternResponse.class);
        return exchange.getBody();
    }

}
