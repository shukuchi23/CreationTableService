package com.example.creationtablesserver.utils;

import com.example.creationtablesserver.model.User;
import com.example.creationtablesserver.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class AuthenticationResolverImpl implements AuthenticationResolver{
    private final UserService userService;
    public AuthenticationResolverImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getUser(authentication);
    }

    @Override
    public User getUser(Authentication authentication) throws AuthenticationResolveException {
        if (authentication == null) {
            throw new BadCredentialsException("No credentials presented");
        }
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            return userService.getByUsername(token.getName()).orElseThrow(NoSuchElementException::new);
        }
        throw new AuthenticationResolveException("Unknown Authentication " + authentication.getClass().getCanonicalName());
    }
}
