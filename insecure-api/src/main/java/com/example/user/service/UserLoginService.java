package com.example.user.service;

import com.example.user.entity.UserEntityRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserLoginService {
    private final UserEntityRepository userEntityRepository;
    private final SecretKey secretKey;
    private JWSSigner signer;

    public UserLoginService(UserEntityRepository userEntityRepository, SecretKey secretKey) {
        this.userEntityRepository = userEntityRepository;
        this.secretKey = secretKey;
    }

    @PostConstruct
    void initSigner() throws KeyLengthException {
        this.signer = new MACSigner(this.secretKey);
    }

    public String login(User user) throws BadCredentialsException {
        try {
            return generateJwt(user);
        } catch (JOSEException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    public Optional<User> findByEmail(String email) {
        return userEntityRepository.findByEmail(email).map(User::new);
    }

    private String generateJwt(User user) throws JOSEException {

        Map<String, Object> payload = new HashMap<>();

        payload.put("iss", "vehicle-service");
        payload.put("exp", Math.abs(System.currentTimeMillis() / 1000) + (120 * 60));
        payload.put("aud", new String[]{"vehicle-service"});
        payload.put("sub", user.getIdentifier());
        payload.put("scope", "openid email profile");
        payload.put("roles", user.getRoles());
        payload.put("preferred_username", user.getEmail());
        payload.put("given_name", user.getFirstName());
        payload.put("family_name", user.getLastName());
        payload.put("email", user.getEmail());
        payload.put("email_verified", true);
        payload.put("name", String.format("%s %s", user.getFirstName(), user.getLastName()));

        return createJwt(payload);
    }

    private String createJwt(Map<String, Object> payload) throws JOSEException {

        JWSObject jwsObject = new JWSObject(
                new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build(),
                new Payload(payload));

        jwsObject.sign(signer);
        return jwsObject.serialize();
    }
}
