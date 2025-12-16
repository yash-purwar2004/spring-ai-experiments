package com.project.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptTemplateController {
    private final ChatClient chatClient;
    public PromptTemplateController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

   
    @Value("classpath:/promptTemplate/userPromptTemplate.st") // injecting the prompt template
    Resource userPromptTemplate;

    @GetMapping("/email")
    public String emailResponse(@RequestParam("customerName") String customerName, @RequestParam("customerMessage")  String customerMessage) {
        // call method is used to take care of invoking llm model
        // content method is used to return a actual content coming from the llm model
        return chatClient.prompt().system(
            """
               You are an email assistant that helps draft professional responses to customer inquiries.
               When given a customer's name and message, you will generate a polite and helpful email response.
               Ensure the tone is friendly and professional.     
            """
        ).user(promptTemplateSpec -> promptTemplateSpec.text(userPromptTemplate)
            .param("customerName", customerName)
            .param("customerMessage", customerMessage)

        ).call().content();
    }
}
