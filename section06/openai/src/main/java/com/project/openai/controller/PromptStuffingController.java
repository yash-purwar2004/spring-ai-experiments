package com.project.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.Duration;
import reactor.core.publisher.Flux;

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
    public Flux<String> promptStuffing(@RequestParam("message") String message) {
        // call method is used to take care of invoking llm model
        // content method is used to return a actual content coming from the llm model
        return chatClient.prompt()
        .options(OpenAiChatOptions.builder().model("gpt-4.1-mini").maxTokens(100)
        .temperature(0.8).build())
        .system(systemPromptTemplate)
        .user(message).stream().content().delayElements(Duration.ofMillis(200));

        // by using entity() method, JSON response is send by the LLM Model
        // the main purpose of flux is to handle multiple data items asynchronously and it not block the thread while waiting for data
    }
}
