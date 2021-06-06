package com.example.creationtablesserver.model.table.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class ColumnDTO {
        private String columnName ;
        @JsonProperty(value = "type")
        private String columnType;

        public ColumnDtoWithPkAndFkField withPkAndFkFields(){
                return new ColumnDtoWithPkAndFkField(this);
        }

        @Getter @Setter
        public static class ColumnDtoWithPkAndFkField extends ColumnDTO{
                private boolean primary = false;
                private Long ref_table = null;
                private String ref_column = null;
                private Boolean multiple = null;

                public ColumnDtoWithPkAndFkField(ColumnDTO origin){
                        this.setColumnName(origin.getColumnName());
                        this.setColumnType(origin.getColumnType());
                }

        }

        public ColumnDTO() {}

}
