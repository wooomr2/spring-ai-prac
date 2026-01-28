package com.woo.ai;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class AiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(@Qualifier("openAiWebClient") WebClient webClient) {
        return args -> {
            System.out.println("OpenAI WebClient Bean Test");

            String response = webClient.get()
                    .uri("/models")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("OpenAI Response: " + response);
        };
    }
}
