package com.example.security;

import com.example.user.service.UserLoginService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserLoginDetailsService implements UserDetailsService {

    private final UserLoginService userLoginService;

    public UserLoginDetailsService(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userLoginService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user for " + username));
    }
}
