package com.woo.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final ChatClient chatClient;

    private final String systemPrompt = """
            당신은 전문 영화 평론가입니다.
            사용자가 입력한 정보를 기반으로 영화를 추천하세요.
            사용자 입력: {userInput}
            """;

    public String recommendMovies(String userInput) {
        PromptTemplate template = new PromptTemplate(systemPrompt);
        Prompt prompt = template.create(Map.of("userInput", userInput));

        return chatClient.prompt(prompt)
                .call()
                .content();
    }

    public Flux<String> streamRecommend(String userInput) {
        PromptTemplate template = new PromptTemplate(systemPrompt);
        Prompt prompt = template.create(Map.of("userInput", userInput));

        return chatClient.prompt(prompt)
                .stream()
                .content()
                .map(chunk -> chunk == null ? ""
                        : chunk.startsWith(" ") ?
                        " " + chunk
                        : chunk)
                .concatWith(Flux.just("[[END]]")); // 스트리밍 종료 신호
    }
}
