package com.project.openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chat(ChatClient.Builder chatClientBuilder){
        ChatOptions chatOptions = ChatOptions.builder().model("gpt-4.1-mini").maxTokens(100)
        .temperature(0.8).build();

        return chatClientBuilder
        .defaultOptions(chatOptions)
        .defaultAdvisors(new SimpleLoggerAdvisor()) // a simple logging advisor to log requests and responses
        .defaultSystem("""
                You are an internal HR assistant. Your role is to help\s
                employees with questions related to HR policies, such as\s
                leave policies, working hours, benefits, and code of conduct.
                If a user asks for help with anything outside of these topics,\s
                kindly inform them that you can only assist with queries related to\s
                """)
                .defaultUser("How can you help me?").build();
    }
}
