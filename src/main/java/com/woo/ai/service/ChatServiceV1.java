package com.woo.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatServiceV1 {

    private final ChatClient chatClient;

    public String chatMemory(String userInput, String userId) {
        return chatClient.prompt()
                .user(userInput)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, userId))
                .call()
                .content();
    }

    public Flux<String> chatMemoryStream(String userInput, String userId) {

        return chatClient.prompt()
                .user(userInput)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, userId))
                .stream()     // 스트리밍 모드로 호출
                .content()    // Flux<String> 반환
                .map(chunk -> (chunk == null) ? "" : (chunk.startsWith(" ") ? " " + chunk : chunk))
                .concatWith(Flux.just("[[END]]")); // 스트리밍 종료 신호
    }
}
