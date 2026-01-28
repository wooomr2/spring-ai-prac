package com.woo.ai.controller;

import com.woo.ai.service.WebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class WebClientController {

    private final WebClientService webClientService;

    @GetMapping("/chat/webclient")
    public Mono<String> chat(@RequestParam("q") String q) {
        return webClientService.getChatCompletion(q);
    }
}
