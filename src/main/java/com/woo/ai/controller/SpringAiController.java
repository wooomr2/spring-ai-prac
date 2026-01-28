package com.woo.ai.controller;

import com.woo.ai.service.SpringAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpringAiController {

    private final SpringAiService springAiService;

    @GetMapping("/chat/springai")
    public String chat(@RequestParam("q") String q) {
        return springAiService.chat(q);
    }
}
