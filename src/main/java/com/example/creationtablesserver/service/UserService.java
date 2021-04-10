package com.example.creationtablesserver.service;

import com.example.creationtablesserver.model.User;
import com.example.creationtablesserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("userService")
public class UserService {
    private UserRepository repository;

    @Transactional
    public List<User> getAll() {
        return repository.findAll();
    }
    @Transactional
    public User getById(Long id) {
        return repository.findById(id).get();
    }

    public UserService() {
    }

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

}
