package com.example.creationtablesserver.service;

import com.example.creationtablesserver.model.project.Project;
import com.example.creationtablesserver.model.user.AuthorityUser;
import com.example.creationtablesserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("userService")
@AllArgsConstructor
public class UserService {
    private UserRepository repository;

    @Qualifier(value = "passwordEncoder")
    private PasswordEncoder encoder;

    @Transactional
    public List<AuthorityUser> getAll() {
        return repository.findAll();
    }

    @Transactional
    public void join_user(@NonNull AuthorityUser authorityUser) {
        // TODO: добавить ошибку существующего пользователя
        // шифруем пароль перед сохранением
        authorityUser.setPassword(encoder.encode(authorityUser.getPassword()));
        repository.save(authorityUser);
    }
    @Transactional
    public void updateUser(@NonNull AuthorityUser authorityUser) {
        repository.save(authorityUser);
    }

    @Transactional
    public AuthorityUser getById(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public Optional<AuthorityUser> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Transactional
    public long getAmount() {
        return repository.count();
    }


}
