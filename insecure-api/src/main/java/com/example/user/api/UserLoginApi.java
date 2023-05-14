package com.example.user.api;

import com.example.user.service.User;
import com.example.user.service.UserLoginService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserLoginApi {

    private final UserLoginService userLoginService;

    public UserLoginApi(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal User user) {
        return userLoginService.login(user);
    }

}
