package com.example.creationtablesserver.rest;

import com.example.creationtablesserver.model.User;
import com.example.creationtablesserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    private UserService service;

    @GetMapping()
    public List<User> getAllUsers() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return service.getById(id);
    }

}
