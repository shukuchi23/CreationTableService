package com.example.creationtablesserver.repository;

import com.example.creationtablesserver.model.META.TableMeta;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<TableMeta, String>
{
}
