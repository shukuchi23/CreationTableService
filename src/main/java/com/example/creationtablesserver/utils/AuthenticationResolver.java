package com.example.creationtablesserver.utils;

import com.example.creationtablesserver.model.user.AuthorityUser;
import org.springframework.security.core.Authentication;

public interface AuthenticationResolver {
    AuthorityUser getUser();
    AuthorityUser getUser(Authentication authentication);
}
