package com.example.creationtablesserver.security;

import com.example.creationtablesserver.enums.Role;
import com.example.creationtablesserver.model.user.AuthorityUser;
import com.example.creationtablesserver.service.UserService;
import com.example.creationtablesserver.utils.AuthenticationResolver;
import org.springframework.stereotype.Component;

@Component("securityChecker")
public class SecurityChecker {
    private final AuthenticationResolver resolver;
    private final UserService userService;

    /* @Autowired не нужен т.к. у нас 1 публичный конструктор*/
    public SecurityChecker(AuthenticationResolver resolver, UserService userService) {
        this.resolver = resolver;
        this.userService = userService;
    }

    public boolean checkUserId(Long id, AuthorityUser requester) {
        if (requester.getUser_id().equals(id))
            return true;
        return requester.getRole() == Role.ADMIN;
    }

    public boolean checkUserId(Long id) {
        return checkUserId(id, getCurrentUser());
    }

    private AuthorityUser getCurrentUser() {
        return resolver.getUser();
    }
}
