package com.example.creationtablesserver.model.META;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ColumnId implements Serializable {
        private String table_name;
        private String column_name;

        public ColumnId() {
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ColumnId columnId = (ColumnId) o;
                return table_name.equals(columnId.table_name) &&
                 column_name.equals(columnId.column_name);
        }

        @Override
        public int hashCode() {
                return Objects.hash(table_name, column_name);
        }

        public String getTable_name() {
                return table_name;
        }

        public void setTable_name(String table_name) {
                this.table_name = table_name;
        }

        public String getColumn_name() {
                return column_name;
        }

        public void setColumn_name(String column_name) {
                this.column_name = column_name;
        }

        public ColumnId(String table_name, String column_name) {
                this.table_name = table_name;
                this.column_name = column_name;
        }

        @Override
        public String toString() {
                return "\nColumnId{" +
                 "table_name='" + table_name + '\'' +
                 ", column_name='" + column_name + '\'' +
                 '}';
        }
}
