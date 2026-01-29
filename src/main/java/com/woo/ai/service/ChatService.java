package com.woo.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public ChatService(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;

        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("세상은 크고 구원은 세상의 모퉁이에 있습니다."),
                new Document("과거를 향해 앞으로 걸어가고 미래를 향해 되돌아갑니다.", Map.of("meta2", "meta2"))
        );
        vectorStore.add(documents);
    }

    public String chatMemory(String userInput, String userId) {
        return chatClient.prompt()
                .user(userInput)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, userId))
                .call()
                .content();
    }

    public Flux<String> chatMemoryStream(String userInput, String userId) {
        Advisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(
                        VectorStoreDocumentRetriever.builder()
                                .similarityThreshold(0.50)
                                .vectorStore(vectorStore)
                                .build()
                )
                .build();

        return chatClient.prompt()
                .user(userInput)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, userId))
                .advisors(retrievalAugmentationAdvisor)
                .stream()
                .content()
                .map(chunk -> (chunk == null) ? "" : (chunk.startsWith(" ") ? " " + chunk : chunk))
                .concatWith(Flux.just("[[END]]"));
    }
}