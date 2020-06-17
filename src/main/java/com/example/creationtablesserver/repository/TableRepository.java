package com.example.creationtablesserver.repository;

import com.example.creationtablesserver.model.META.TableMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@Repository("tableRepository")
@EnableJpaRepositories
public interface TableRepository extends JpaRepository<TableMeta, String> {
}
