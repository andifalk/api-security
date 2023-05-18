package com.example.user.api;

import com.example.user.service.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserLoginApi {

    @GetMapping("/me")
    public User me(@AuthenticationPrincipal User user) {
        return user;
    }

}
