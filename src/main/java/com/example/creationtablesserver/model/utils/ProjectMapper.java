package com.example.creationtablesserver.model.utils;

import com.example.creationtablesserver.model.project.DTO.ProjectDTO;
import com.example.creationtablesserver.model.project.Project;
import com.example.creationtablesserver.model.table.DTO.ColumnDTO;
import com.example.creationtablesserver.model.table.DTO.TableDTO;
import com.example.creationtablesserver.model.table.META.ColumnMeta;
import com.example.creationtablesserver.model.table.META.FkeyMeta;
import com.example.creationtablesserver.model.table.META.TableMeta;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectMapper {

    public static Project MetaFromDto(ProjectDTO dtoProject) {
        Project metaProject = new Project();
        metaProject.setDatabase(dtoProject.getDatabase());
        metaProject.setProjectName(dtoProject.getProjName());

        List<TableDTO.NormalTableDto> dtoTables = dtoProject
                .getTables().stream()
                .map(TableDTO::getNormalVersion)
                .collect(Collectors.toList());

        Map<Long, TableMeta> metas = new HashMap<>(dtoProject.getTables().size());
        for (TableDTO.NormalTableDto table : dtoTables) {
            TableMeta tableMeta = new TableMeta();
            tableMeta.setTable_name(table.getName());

            // Добавление столбцов таблицам проекта (без внешних ключей)
            List<ColumnMeta> columns = new LinkedList<>();
            for (ColumnDTO.ColumnDtoWithPkAndFkField columnDto : table.getColumns()) {
                ColumnMeta columnMeta = new ColumnMeta();
                columnMeta.setColumn_name(columnDto.getColumnName());
                columnMeta.setColumn_type(columnDto.getColumnType());
                columnMeta.setCoolTable(tableMeta);
                columnMeta.setPr_key(columnDto.isPrimary());
                columns.add(columnMeta);
            }
            tableMeta.setColumns(columns);
            metas.put(table.getTableId(), tableMeta);
        }
        // Установка внешних ключей на первичные
        for (TableDTO.NormalTableDto dtoTable : dtoTables) {
            List<ColumnDTO.ColumnDtoWithPkAndFkField> fkColumns = dtoTable
                    .getColumns().stream()
                    .filter(col -> col.getRef_table() != null)
                    .collect(Collectors.toList());
            for (ColumnDTO.ColumnDtoWithPkAndFkField fk : fkColumns) {
                //
                //  (primary key) refColumn <---------  ownerColumn (foreign key)
                //
                try {
                    ColumnMeta refColumn = metas.get(fk.getRef_table()).findColumnByName(fk.getRef_column());
                    if (!refColumn.getPr_key())
                        throw new RuntimeException("Reference column must be primary key");
                    TableMeta ownerTable = metas.get(dtoTable.getTableId());
                    ColumnMeta ownerColumn = ownerTable.findColumnByName(fk.getColumnName());
                    ownerColumn.setReference(new FkeyMeta(fk.getMultiple(), refColumn));
                    metas.put(dtoTable.getTableId(), ownerTable);
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        metas.keySet().forEach(k -> metaProject.addTable(metas.get(k)));
        return metaProject;
    }

}
