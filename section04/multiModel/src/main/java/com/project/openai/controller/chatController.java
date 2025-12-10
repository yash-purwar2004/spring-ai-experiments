package com.project.openai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class chatController {
    // using the default chatClient bean, we should be able to chat with a LLM Model
    private final ChatClient openAiChatClient;
    private final ChatClient ollamaChatClient;

    public chatController(@Qualifier("openAiChatClient") ChatClient openAiChatClient, @Qualifier("ollamaChatClient") ChatClient ollamChatClient){
        this.openAiChatClient = openAiChatClient;
        this.ollamaChatClient = ollamChatClient;
    }

    @GetMapping("/openai/chat")
    public String openAIChat(@RequestParam("message") String message) {
        // call method is used to take care of invoking llm model
        // content method is used to return a actual content coming from the llm model
        return openAiChatClient.prompt(message).call().content();
    }

    @GetMapping("/ollama/chat")
    public String ollamaChat(@RequestParam("message") String message) {
        // call method is used to take care of invoking llm model
        // content method is used to return a actual content coming from the llm model
        return ollamaChatClient.prompt(message).call().content();
    }

    
}
