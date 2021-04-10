package com.example.creationtablesserver.rest;

import com.example.creationtablesserver.model.User;
import com.example.creationtablesserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/users")
public class UserRestController {

    private UserService service;

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return service.getById(id);
    }

    @PostMapping("/")
    public String createUser(User user) {
        return null;
    }
    @Autowired
    public UserRestController(UserService service) {
        this.service = service;
    }

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }


}
