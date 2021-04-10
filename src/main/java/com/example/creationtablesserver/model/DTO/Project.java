package com.example.creationtablesserver.model.DTO;

import com.example.creationtablesserver.model.Database;
import com.example.creationtablesserver.model.User;
import lombok.Data;

import java.util.List;

@Data
public class Project {
    private String project_name;
    private List<TableDTO> tables;
    private User owner;
    private Database database;

}
