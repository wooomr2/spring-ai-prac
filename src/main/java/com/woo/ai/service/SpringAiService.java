package com.woo.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringAiService {

    private final ChatClient chatClient;

    public String chat(String q) {
        return chatClient.prompt()
                .user(q)
                .call()
                .content();
    }
}
