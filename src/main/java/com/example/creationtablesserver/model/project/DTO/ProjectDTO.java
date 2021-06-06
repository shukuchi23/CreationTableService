package com.example.creationtablesserver.model.project.DTO;

import com.example.creationtablesserver.model.project.Project;
import com.example.creationtablesserver.model.table.DTO.ColumnDTO;
import com.example.creationtablesserver.model.table.DTO.ForeignKey;
import com.example.creationtablesserver.model.table.DTO.PrimaryKey;
import com.example.creationtablesserver.model.table.DTO.TableDTO;
import com.example.creationtablesserver.model.table.META.ColumnMeta;
import com.example.creationtablesserver.model.table.META.FkeyMeta;
import com.example.creationtablesserver.model.table.META.TableMeta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProjectDTO extends ShortProjectDTO {
    private String login;
    private List<TableDTO> tables;

    public static ProjectDTO fromEntity(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjId(project.getProjectId());
        projectDTO.setProjName(project.getProjectName());
        projectDTO.setDatabase(project.getDatabase());
        List<TableDTO> tables = new LinkedList<>();
        for (TableMeta tableMeta : project.getTables()) {
            TableDTO tableDTO = new TableDTO();
            tableDTO.setTableId(tableMeta.getTable_id());
            tableDTO.setName(tableMeta.getTable_name());
            List<ColumnMeta> columns = tableMeta.getColumns();
            tableDTO.setPrimaryKeys(
                    columns.stream()
                            .filter(ColumnMeta::getPr_key)
                            .map(pkColumn -> new PrimaryKey(pkColumn.getColumn_name()))
                            .collect(Collectors.toList())
            );
            List<ColumnMeta> fkColumns = columns.stream()
                    .filter(c -> c.getFkey() != null)
                    .collect(Collectors.toList());

            List<ForeignKey> fkeys = new LinkedList<>();
            for (ColumnMeta fk : fkColumns) {
                FkeyMeta fkey = fk.getFkey();
                fkeys.add(new ForeignKey(
                        fk.getColumn_name(),
                        fkey.getRefColumn().getColumn_name(),
                        fkey.getRefColumn().getCoolTable().getTable_id(),
                        fkey.getMultiply())
                );
            }
            tableDTO.setForeignKeys(fkeys);
            tableDTO.setColumns(columns
                    .stream()
                    .map(c -> new ColumnDTO(c.getColumn_name(), c.getColumn_type()))
                    .collect(Collectors.toList())
            );
            tables.add(tableDTO);
        }
        tables.sort(Comparator.comparingLong(TableDTO::getTableId));
        projectDTO.setTables(tables);
        projectDTO.setLogin(project.getOwner().getUsername());
        return projectDTO;
    }
}
