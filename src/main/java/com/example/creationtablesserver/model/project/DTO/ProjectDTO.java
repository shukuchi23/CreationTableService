package com.example.creationtablesserver.model.project.DTO;

import com.example.creationtablesserver.model.project.Project;
import com.example.creationtablesserver.model.table.DTO.TableDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProjectDTO extends ShortProjectDTO{
    private String login;
    private List<TableDTO> tables;

    // TODO: написать
    public static ProjectDTO fromProject(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjId(project.getProjectId().getProject_id());
        projectDTO.setProjName(project.getProjectName());
        projectDTO.setDatabase(project.getDatabase());

//        projectDTO.setTables();
        projectDTO.setLogin(project.getOwner().getUsername());
        return projectDTO;
    }
}