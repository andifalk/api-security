package com.example.apigateway.security;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfiguration {

    private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    public WebSecurityConfiguration(OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
    }

    @Order(1)
    @Bean
    SecurityWebFilterChain actuatorHttpSecurity(ServerHttpSecurity http) {
        http
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/actuator/**"))
                .authorizeExchange((exchanges) -> exchanges
                        .anyExchange().permitAll()
                );
        return http.build();
    }

    @Bean
    SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
        http
                .authorizeExchange((exchanges) -> exchanges
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(o -> o.jwt(Customizer.withDefaults()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

    @Primary
    @Bean
    NimbusReactiveJwtDecoder jwtDecoder() {
        NimbusReactiveJwtDecoder jwtDecoder = NimbusReactiveJwtDecoder
                .withJwkSetUri(oAuth2ResourceServerProperties.getJwt().getJwkSetUri())
                .validateType(false).build();
        jwtDecoder.setJwtValidator(JwtValidators.createDefaultWithValidators(
                List.of(
                        JwtValidators.createAtJwtValidator()
                                .audience("demo-client-jwt-pkce")
                                .clientId("demo-client-jwt-pkce")
                                .issuer("http://localhost:9500").build())));
        return jwtDecoder;
    }
}
