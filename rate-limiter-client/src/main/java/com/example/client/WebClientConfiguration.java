package com.example.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient(@Value("${target.url}") String url, @Value("${jwt}") String jwt) {
        return WebClient
                .builder()
                .baseUrl(url)
                .defaultHeader("Authorization", "Bearer " + jwt)
                .build();
    }
}
