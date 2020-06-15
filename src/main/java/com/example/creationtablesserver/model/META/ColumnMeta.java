package com.example.creationtablesserver.model.META;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "column_meta", schema = "tech_test")
public class ColumnMeta implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id_c")
        private Integer id;

        @Column(name = "column_name")
        private String column_name;

        @Column(name = "column_type")
        private String column_type;

        @Column(name = "pr_key")
        private Boolean pr_key;

        @Override
        public String toString() {
                return "ColumnMeta{" +
                 "id=" + id +
                 ", column_name='" + column_name + '\'' +
                 ", column_type='" + column_type + '\'' +
                 ", pr_key=" + pr_key +
                 ", tableName=" + table.getTable_name() +
                 '}';
        }



        public Boolean getPr_key() {
                return pr_key;
        }

        public void setPr_key(Boolean pr_key) {
                this.pr_key = pr_key;
        }

        @Column (name = "create_date")
        private LocalDateTime create_date;

        @Column (name = "update_date")
        private LocalDateTime update_date;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "id_t")
        private TableMeta table;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getColumn_name() {
                return column_name;
        }

        public void setColumn_name(String column_name) {
                this.column_name = column_name;
        }

        public String getColumn_type() {
                return column_type;
        }

        public void setColumn_type(String column_type) {
                this.column_type = column_type;
        }

        public TableMeta getTable() {
                return table;
        }

        public void setTable(TableMeta table) {
                this.table = table;
        }

        public LocalDateTime getCreate_date() {
                return create_date;
        }

        public void setCreate_date(LocalDateTime create_date) {
                this.create_date = create_date;
        }

        public LocalDateTime getUpdate_date() {
                return update_date;
        }

        public void setUpdate_date(LocalDateTime update_date) {
                this.update_date = update_date;
        }

        @PrePersist
        public void toCreate() {
                setCreate_date(LocalDateTime.now());
        }

        @PreUpdate
        public void toUpdate() {
                setUpdate_date(LocalDateTime.now());
        }

        public ColumnMeta(){}
}
