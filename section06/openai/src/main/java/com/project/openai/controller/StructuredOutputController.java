package com.project.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.openai.model.CountryCities;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/structured-output")
public class StructuredOutputController {
    
    private final ChatClient chatClient;

    public StructuredOutputController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat-bean")
    public ResponseEntity<CountryCities> chatBean(@RequestParam("message") String message) {
        CountryCities countryCities = chatClient
        .prompt()
        .user(message)
        .call()
        .entity(CountryCities.class);
        return ResponseEntity.ok(countryCities);
    }

    // Inject ChatClient → if behavior is same
    // Inject Builder → if behavior differs
    
}
