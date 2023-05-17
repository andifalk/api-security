package com.example.partner.api;

import com.example.user.service.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/partner")
public class LegacyPartnerApi {

    @GetMapping
    public String readPartnerData(@AuthenticationPrincipal User user) {
        return String.format("Here is your data for partner %s (%s %s)",
                Objects.requireNonNull(user).getEmail(), user.getFirstName(), user.getLastName());
    }
}
