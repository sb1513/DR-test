package com.backend.drbackend.config;

import com.backend.drbackend.utils.aiTools.DateTool;
import com.backend.drbackend.utils.aiTools.EmailTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    @Autowired
    private DateTool dateTool;
    @Autowired
    private EmailTool emailTool;

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();
    }

    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        System.out.println("chatClient");
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultSystem("你是一个小助手，回答用户的问题.如果遇到你无法解决的问题，可以尝试调用工具")
                .defaultTools(dateTool, emailTool)
                .build();
    }
}