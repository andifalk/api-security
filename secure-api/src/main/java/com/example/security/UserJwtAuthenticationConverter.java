package com.example.security;

import com.example.user.service.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final List<String> ROLE_CLAIMS = List.of("roles", "permissions");
    private static final String FIRST_NAME_CLAIM = "given_name";
    private static final String LAST_NAME_CLAIM = "family_name";
    private static final String EMAIL_CLAIM = "email";

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        User productUser = new User(
                UUID.fromString(jwt.getSubject()), jwt.getClaimAsString(FIRST_NAME_CLAIM), jwt.getClaimAsString(LAST_NAME_CLAIM),
                jwt.getClaimAsString(EMAIL_CLAIM), "n/a", getRolesFromToken(jwt));
        return new UserJwtAuthenticationToken(productUser, jwt, productUser.getAuthorities());
    }

    private List<String> getRolesFromToken(Jwt jwt) {
        List<String> roles = new ArrayList<>();
        for (String claim : ROLE_CLAIMS) {
            if (jwt.hasClaim(claim)) {
                roles.addAll(jwt.getClaimAsStringList(claim));
            }
        }
        return roles.stream().map(String::toUpperCase).toList();
    }
}
