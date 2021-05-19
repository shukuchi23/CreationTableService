package com.example.creationtablesserver.model.project.DTO;

import com.example.creationtablesserver.model.Database;
import com.example.creationtablesserver.model.project.Project;
import lombok.Data;

@Data
public class ShortProjectDTO {
    private Long projId;
    private String projName;
    private Database database;

    public static ShortProjectDTO fromProject(Project project) {
        ShortProjectDTO dto = new ShortProjectDTO();

        dto.setDatabase(project.getDatabase());
        dto.setProjId(project.getProjectId().getProject_id());
        dto.setProjName(project.getProj_name());

        return dto;
    }
}
