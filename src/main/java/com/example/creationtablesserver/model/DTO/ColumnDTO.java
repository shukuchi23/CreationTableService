package com.example.creationtablesserver.model.DTO;

public class ColumnDTO {
        private String columnName ;
        private String type ;

        public ColumnDTO() {}

        public String getColumnName() {
                return columnName;
        }

        public void setColumnName(String columnName) {
                this.columnName = columnName;
        }

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }
}
