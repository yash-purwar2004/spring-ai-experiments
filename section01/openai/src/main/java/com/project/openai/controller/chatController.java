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
    private final ChatClient chatClient;

    public chatController(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        // call method is used to take care of invoking llm model
        // content method is used to return a actual content coming from the llm model
        return chatClient.prompt(message).call().content();
    }
    
}
