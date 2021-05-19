package com.example.creationtablesserver.model.table.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ColumnDTO {
        private String columnName ;
        private String type ;

        public ColumnDtoWithPkAndFkField withPkAndFkFields(){
                ColumnDtoWithPkAndFkField upgrade = new ColumnDtoWithPkAndFkField(this);
                return upgrade;
        }
        @Getter @Setter
        public static class ColumnDtoWithPkAndFkField extends ColumnDTO{
                private boolean primary = false;
                private Long ref_table = null;
                private String ref_column = null;
                private Boolean multiple = null;

                public ColumnDtoWithPkAndFkField(ColumnDTO origin){
                        super();
                        this.setColumnName(origin.getColumnName());
                        this.setType(origin.getType());
                }

        }
        public ColumnDTO() {}

}
