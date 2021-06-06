package com.example.creationtablesserver.model.table.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class OldForeignKey implements Serializable {
    private String name;
    private String key;
    private String referenceTable;
}
