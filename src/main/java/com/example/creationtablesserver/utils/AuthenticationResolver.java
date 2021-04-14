package com.example.creationtablesserver.utils;

import com.example.creationtablesserver.model.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationResolver {
    User getUser();
    User getUser(Authentication authentication);
}
