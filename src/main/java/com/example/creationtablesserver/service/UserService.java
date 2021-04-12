package com.example.creationtablesserver.service;

import com.example.creationtablesserver.model.User;
import com.example.creationtablesserver.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("userService")
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    public List<User> getAll() {
        return repository.findAll();
    }
    @Transactional
    public void join_user(@NonNull User user) {
        // TODO: добавить ошибку существующего пользователя

        // шифруем пароль перед сохранением
        user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
        repository.save(user);
    }

    @Transactional
    public User getById(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public long getAmount() {
        return repository.count();
    }

    /*public UserService() {
    }*/

   /* @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }*/

}
