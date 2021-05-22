package com.example.creationtablesserver.repository;

import com.example.creationtablesserver.model.project.Project;
import com.example.creationtablesserver.model.table.META.embeddable.ProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository<T> extends JpaRepository<Project, T> {
}
