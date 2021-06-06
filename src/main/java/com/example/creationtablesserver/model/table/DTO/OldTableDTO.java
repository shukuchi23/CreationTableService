package com.example.creationtablesserver.model.table.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OldTableDTO implements Serializable {
    private String name;

    private List<ColumnDTO> columns;

    private List<OldForeignKey> foreignKeys;
    private List<PrimaryKey> primaryKeys;
}
