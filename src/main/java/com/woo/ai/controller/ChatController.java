package com.woo.ai.controller;

import com.woo.ai.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat")
    public String memory(@RequestParam String id, @RequestParam String q) {
        return chatService.chatMemory(q, id);
    }

    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> memoryStream(@RequestParam("id") String id,
                                                      @RequestParam("q") String q) {
        return chatService.chatMemoryStream(q, id)
                .map(chunk -> ServerSentEvent.builder(chunk).build());
    }
}
