package com.example.creationtablesserver.model.table.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class TableDTO implements Serializable {
    private Long tableId;  // относительно проекта
    private String name;

    private List<ColumnDTO> columns;

    private List<ForeignKey> foreignKeys;
    private List<PrimaryKey> primaryKeys;

    public NormalTableDto getNormalVersion() {
        return new NormalTableDto(this);
    }

    @Data
    public static class NormalTableDto {
        private String name;
        private Long tableId;  // относительно проекта
        public List<ColumnDTO.ColumnDtoWithPkAndFkField> columns = new LinkedList<>();

        public NormalTableDto(TableDTO origin) {
            this.name = origin.name;
            this.tableId = origin.tableId;
            Set<String> pkSet = new HashSet<>();
            if (origin.primaryKeys != null) {
                pkSet = origin.primaryKeys
                        .stream()
                        .map(PrimaryKey::getKey)
                .collect(Collectors.toSet());
            }

            Map<String, ForeignKey> fkMap = new HashMap<>();
            if (origin.foreignKeys != null) {
                fkMap = origin.foreignKeys.stream()
                        .collect(Collectors.toMap(ForeignKey::getName, Function.identity()));
            }

            for (ColumnDTO x : origin.columns) {
                ColumnDTO.ColumnDtoWithPkAndFkField tmp = x.withPkAndFkFields();
                if (pkSet.contains(x.getColumnName())) {
                    tmp.setPrimary(true);
                    pkSet.remove(tmp.getColumnName());
                }
                ForeignKey fkTmp = fkMap.get(tmp.getColumnName());
                if (fkTmp != null) {
                    tmp.setRef_table(fkTmp.getReferenceTable());
                    tmp.setRef_column(fkTmp.getKey());
                    tmp.setMultiple(fkTmp.getMultiple());
                    fkMap.remove(tmp.getColumnName());
                }
                columns.add(tmp);
            }
        }

    }

    public TableDTO() {
    }


}
