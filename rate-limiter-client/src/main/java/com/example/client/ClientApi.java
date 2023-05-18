package com.example.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class ClientApi {
    private static final Logger LOG = LoggerFactory.getLogger(ClientApi.class);
    private final WebClient webClient;

    public ClientApi(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/api/rate")
    public Flux<CommunityPost> rateLimiting(@RequestParam("requests") int numRequests, @RequestParam("delay") long delay) {
        return Flux.range(1, numRequests)
            //.log()
            .delayElements(Duration.ofMillis(delay))
            .flatMap(t ->
                    webClient.get()
                            .uri("/api/v1/community")
                            .retrieve()
                            .bodyToFlux(CommunityPost.class)
                            //.log()
                            .onErrorResume(
                                    throwable -> throwable instanceof WebClientResponseException.TooManyRequests,
                                    throwable -> { LOG.info("Got {}", ((WebClientResponseException) throwable).getStatusCode()) ;return Mono.just(new CommunityPost()); })
                            //.log()
            );
    }
}
