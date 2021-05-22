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
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public List<AuthorityUser> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void join_user(@NonNull AuthorityUser authorityUser) {
        // TODO: добавить ошибку существующего пользователя
        // шифруем пароль перед сохранением
        authorityUser.setPassword(passwordEncoder.encode(authorityUser.getPassword()));
        userRepository.save(authorityUser);
    }
    @Transactional
    public void updateUser(@NonNull AuthorityUser authorityUser) {
        userRepository.save(authorityUser);
    }

    @Transactional
    public AuthorityUser getById(Long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public Optional<AuthorityUser> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public long getAmount() {
        return userRepository.count();
    }


}
