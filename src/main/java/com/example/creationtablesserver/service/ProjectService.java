package com.example.creationtablesserver.service;

import com.example.creationtablesserver.model.project.Project;
import com.example.creationtablesserver.model.table.META.embeddable.ProjectId;
import com.example.creationtablesserver.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
//    private ProjectRepository<Long> repository;
    private ProjectRepository<ProjectId> repository;
    @Transactional
    public Project getById(ProjectId id) {
        return repository.findById(id).get();
    }
    /*@Transactional
    public Project getById(Long id) {
        repository.findById()
        return repository.findById(id).get();
    }*/
    @Transactional
    public void dropById(ProjectId id) {
        repository.deleteById(id);
    }
   /* @Transactional
    public void dropById(Long id) {
        repository.deleteById(id);
    }*/

    @Transactional
    public void addProject(Project project){
        repository.save(project);
    }

    @Transactional
    public List<Project> getAll() {
        return repository.findAll();
    }

}
