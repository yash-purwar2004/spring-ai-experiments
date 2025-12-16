package com.project.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptStuffingController {
    private final ChatClient chatClient;
    public PromptStuffingController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

   
    @Value("classpath:/promptTemplate/systemPromptTemplate.st") // injecting the prompt template
    Resource systemPromptTemplate;

    @GetMapping("/prompt-stuffing")
    public String promptStuffing(@RequestParam("message") String message) {
        // call method is used to take care of invoking llm model
        // content method is used to return a actual content coming from the llm model
        return chatClient.prompt()
        .options(OpenAiChatOptions.builder().model("gpt-4.1-mini").maxTokens(100)
        .temperature(0.8).build())
        .system(systemPromptTemplate)
        .user(message).call().content();
    }
}
