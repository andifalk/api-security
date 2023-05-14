package com.example.partner.api;

import com.example.user.service.User;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/partner")
public class LegacyPartnerApi {
    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$", 2);
    private final Converter<Map<String, Object>, Map<String, Object>> claimSetConverter = MappedJwtClaimSetConverter
            .withDefaults(Collections.emptyMap());
    private final Converter<Jwt, AbstractAuthenticationToken> authenticationConverter;

    public LegacyPartnerApi(Converter<Jwt, AbstractAuthenticationToken> authenticationConverter) {
        this.authenticationConverter = authenticationConverter;
    }

    @GetMapping
    public String readPartnerData(@RequestHeader("Authorization") String authzHeader) {
        User user = parsePartnerJwt(authzHeader);
        return String.format("Here is your data for partner %s (%s %s)",
                Objects.requireNonNull(user).getEmail(), user.getFirstName(), user.getLastName());
    }

    private User parsePartnerJwt(String authorization) {
        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            return null;
        } else {
            Matcher matcher = authorizationPattern.matcher(authorization);
            if (!matcher.matches()) {
                BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
                throw new OAuth2AuthenticationException(error);
            } else {
                String token = matcher.group("token");
                Jwt jwt;
                try {
                    JWT parsedJwt = JWTParser.parse(token);
                    Map<String, Object> headers = new LinkedHashMap<>(parsedJwt.getHeader().toJSONObject());
                    Map<String, Object> claims = this.claimSetConverter.convert(parsedJwt.getJWTClaimsSet().getClaims());
                    jwt = Jwt.withTokenValue(token)
                            .headers((h) -> h.putAll(headers))
                            .claims((c) -> {
                                if (claims != null) {
                                    c.putAll(claims);
                                }
                            }).build();
                } catch (ParseException e) {
                    throw new BadCredentialsException("Invalid Token", e);
                }

                AbstractAuthenticationToken authenticationToken = authenticationConverter.convert(jwt);
                if (authenticationToken != null) {
                    return (User) authenticationToken.getDetails();
                } else {
                    throw new BadCredentialsException("Invalid Token");
                }
            }
        }
    }

}
