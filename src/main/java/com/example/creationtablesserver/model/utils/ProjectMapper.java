package com.example.creationtablesserver.model.utils;

import com.example.creationtablesserver.model.project.DTO.ProjectDTO;
import com.example.creationtablesserver.model.project.Project;
import com.example.creationtablesserver.model.table.DTO.ColumnDTO;
import com.example.creationtablesserver.model.table.DTO.TableDTO;
import com.example.creationtablesserver.model.table.META.ColumnMeta;
import com.example.creationtablesserver.model.table.META.FkeyMeta;
import com.example.creationtablesserver.model.table.META.TableMeta;
import com.example.creationtablesserver.model.table.META.embeddable.ProjectId;
import com.example.creationtablesserver.model.table.META.embeddable.TableId;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectMapper {

    public static Project MetaFromDto(ProjectDTO dtoProject, Long owner_id) {
        Project metaProject = new Project();
        metaProject.setProjectId(new ProjectId(dtoProject.getProjId(), owner_id));
        metaProject.setDatabase(dtoProject.getDatabase());
        metaProject.setProj_name(dtoProject.getProjName());

        List<TableDTO.NormalTableDto> dtoTables = dtoProject
                .getTables().stream()
                .map(TableDTO::getNormalVersion)
                .collect(Collectors.toList());

        List<TableMeta> metas = new LinkedList<>();
        for (TableDTO.NormalTableDto table : dtoTables) {
            TableMeta tableMeta = new TableMeta();
            tableMeta.setTable_id(new TableId(metaProject.getProjectId(), table.getTableId()));
            tableMeta.setTable_name(table.getName());
            tableMeta.setProject(metaProject);

            // Добавление столбцов таблицам проекта (без внешних ключей)
            List<ColumnMeta> columns = new LinkedList<>();
            for(ColumnDTO.ColumnDtoWithPkAndFkField columnDto : table.getColumns()) {
                ColumnMeta columnMeta = new ColumnMeta();
                columnMeta.setColumn_name(columnDto.getColumnName());
                columnMeta.setColumn_type(columnDto.getColumnType());
                columnMeta.setCoolTable(tableMeta);
                columnMeta.setPr_key(columnDto.isPrimary());
                columns.add(columnMeta);
            }
            tableMeta.setColumns(columns);
            metas.add(tableMeta);
        }
        metaProject.setTables(metas);

        // Установка внешних ключей на первичные
        for (TableDTO.NormalTableDto dtoTable : dtoTables){
            List<ColumnDTO.ColumnDtoWithPkAndFkField> fkColumns = dtoTable
                    .getColumns().stream()
                    .filter(col -> col.getRef_table() != null)
            .collect(Collectors.toList());
            for (ColumnDTO.ColumnDtoWithPkAndFkField fk : fkColumns ){
                //
                //  (primary key) refColumn <---------  ownerColumn (foreign key)
                //
                try
                {
                    ColumnMeta refColumn = metaProject.findTableById(fk.getRef_table()).findColumnByName(fk.getRef_column());
                    if (!refColumn.getPr_key())
                        throw new RuntimeException("Reference column must be primary key");

                    ColumnMeta ownerColumn = metaProject.findTableById(dtoTable.getTableId()).findColumnByName(fk.getColumnName());
                    ownerColumn.setFkey(new FkeyMeta(fk.getMultiple(), refColumn));
                }catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return metaProject;
    }

}
