package com.example.creationtablesserver.model.DTO;

import java.util.List;

public class TableDTO {
        private String name;

        private List<ColumnDTO> columns;

        private List<ForeignKey> foreignKeys;
        private List<PrimaryKey> primaryKeys;

        public TableDTO() {
        }

        /*Геттеры, Сеттеры*/
        public ColumnDTO findColumn(String columnName) {
                for (ColumnDTO column : columns) {
                        if (column.getColumnName().equals(columnName))
                                return column;
                }
                return null;
        }

        public ForeignKey findFkeyByColumn(String column) {
                if (foreignKeys != null) {
                        for (ForeignKey fk : foreignKeys) {
                                if (fk.getName().equals(column))
                                        return fk;
                        }
                }
                return null;
        }

        public List<PrimaryKey> getPrimaryKeys() {
                return primaryKeys;
        }

        public void setPrimaryKeys(List<PrimaryKey> primaryKeys) {
                this.primaryKeys = primaryKeys;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public List<ColumnDTO> getColumns() {
                return columns;
        }

        public void setColumns(List<ColumnDTO> columns) {
                this.columns = columns;
        }

        public List<ForeignKey> getForeignKeys() {
                return foreignKeys;
        }

        public void setForeignKeys(List<ForeignKey> foreignKeys) {
                this.foreignKeys = foreignKeys;
        }
}
