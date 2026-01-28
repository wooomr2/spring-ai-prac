package com.woo.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class AiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(@Qualifier("openAiWebClient") WebClient webClient, ObjectMapper objectMapper) {
        return args -> {
            Map<String, Object> requestBody = Map.of(
                    "model", "gpt-4o-mini",
                    "messages", List.of(
                            Map.of(
                                    "role", "user",
                                    "content", "지구의 나이는?"
                            )
                    )
            );

            String response = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("=== OpenAI Response ===");
            System.out.println(response);

            Map<?, ?> result = objectMapper.readValue(response, Map.class);
            List<?> choices = (List<?>) result.get("choices");
            Map<?, ?> firstChoice = (Map<?, ?>) choices.getFirst();
            Map<?, ?> message = (Map<?, ?>) firstChoice.get("message");
            String content = message.get("content").toString();
            System.out.println(content);
        };
    }
}
