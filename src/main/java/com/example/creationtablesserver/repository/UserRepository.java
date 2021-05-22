package com.example.creationtablesserver.repository;

import com.example.creationtablesserver.model.user.AuthorityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AuthorityUser, Long> {
    Optional<AuthorityUser> findByUsername(String username);

}
