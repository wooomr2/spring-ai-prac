package com.woo.ai.controller;

import com.woo.ai.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping(value = "/movie/recommend")
    public String recommend(@RequestParam("q") String q) {
        return movieService.recommendMovies(q);
    }

    @GetMapping(value = "/movie/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam("q") String q) {
        return movieService.streamRecommend(q);
    }
}