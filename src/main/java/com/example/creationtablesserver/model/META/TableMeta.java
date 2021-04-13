package com.example.creationtablesserver.model.META;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "table_meta", schema = "tech")
public class TableMeta implements Serializable {
        //        -- FIELD---
        @Id
        private String table_name;
        private LocalDateTime create_date;
        private LocalDateTime update_date;

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "someTable", cascade = CascadeType.ALL)
        private Set<ColumnMeta> columns;

        //        GET & SET
        public LocalDateTime getUpdate_date() {
                return update_date;
        }

        public void setUpdate_date(LocalDateTime update_date) {
                this.update_date = update_date;
        }

        public Set<ColumnMeta> getColumns() {
                return columns;
        }

        public void setColumns(Set<ColumnMeta> columns) {
                this.columns = columns;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                TableMeta tableMeta = (TableMeta) o;
                return table_name.equals(tableMeta.table_name) &&
                 create_date.equals(tableMeta.create_date);
        }

        @Override
        public int hashCode() {
                return Objects.hash(table_name, create_date);
        }

        public String getTable_name() {
                return table_name;
        }

        public void setTable_name(String table_name) {
                this.table_name = table_name;
        }

        public LocalDateTime getCreate_date() {
                return create_date;
        }

        public void setCreate_date(LocalDateTime create_date) {
                this.create_date = create_date;
        }

        @PrePersist
        public void toCreate() {
                setCreate_date(LocalDateTime.now());
        }

        @PreUpdate
        public void toUpdate() {
                setUpdate_date(LocalDateTime.now());
        }

        public TableMeta() {
        }

        @Override
        public String toString() {
                return "\nTableMeta{" +
                 "table_name='" + table_name + '\'' +
                 ", create_date=" + create_date +
                 ", update_date=" + update_date +
                 ", columns=" + columns +
                 '}';
        }
}
