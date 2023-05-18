# Test Client for Rate Limiter

This is a quite simple client to test if the rate limiter of the gateway really works.

This provides one simple API endpoint:

[http://localhost:9096/api/rate?requests=2&delay=10](http://localhost:9096/api/rate?requests=2&delay=10)

The API requires the following request parameters:

* __requests__: The number of requests to be triggered
* __delay__: The delay to wait between requests (in milliseconds), when `0` is given then no delay is configured

## Configuration

You may configure the base URI and a JWT bearer token in the class `WebClientConfiguration`:

```java
package com.example.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl("http://localhost:9090")
                //.defaultHeader("Authorization", "Bearer <JWT>")
                .build();
    }
}
```


