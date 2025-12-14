package com.project.openai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class chatController {
    // using the default chatClient bean, we should be able to chat with a LLM Model
    private final ChatClient chat;

    public chatController(ChatClient chat){
        this.chat = chat;

    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        // call method is used to take care of invoking llm model
        // content method is used to return a actual content coming from the llm model
        return chat.prompt().system("""
                You are an internal IT helpdesk assistant. Your role is to help\s
                employees with questions related to HR policies, such as\s
                leave policies, working hours, benefits, and code of conduct.
                If a user asks for help with anything outside of these topics,\s
                kindly inform them that you can only assist with queries related to\s
                """)
                .user(message)
                .call().content();
    }
    
}
