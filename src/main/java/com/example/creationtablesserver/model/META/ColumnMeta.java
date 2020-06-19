package com.example.creationtablesserver.model.META;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "column_meta", schema = "tech_test")
public class ColumnMeta implements Serializable {

        @EmbeddedId
        private ColumnId column_pk;

        @Column(name = "column_type")
        private String column_type;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(referencedColumnName = "table_name")
//        @ManyToOne
        private TableMeta someTable;

        @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
        @JoinColumn(name = "fk_id")
        private FkeyMeta fkey;

        public ColumnId getColumn_pk() {
                return column_pk;
        }

        public void setColumn_pk(ColumnId column_pk) {
                this.column_pk = column_pk;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ColumnMeta that = (ColumnMeta) o;
                return column_pk.equals(that.column_pk) &&
                 column_type.equals(that.column_type) &&
                 pr_key.equals(that.pr_key) &&
                 create_date.equals(that.create_date) &&
                 update_date.equals(that.update_date) &&
                 someTable.equals(that.someTable) &&
                 Objects.equals(fkey, that.fkey);
        }

        @Override
        public int hashCode() {
                return Objects.hash(column_pk, column_type, pr_key, create_date, update_date, someTable, fkey);
        }

        @Column(name = "pr_key")
        private Boolean pr_key;

        @Column(name = "create_date")
        private LocalDateTime create_date;

        @Column(name = "update_date")
        private LocalDateTime update_date;

        @Override
        public String toString() {
                return "\nColumnMeta{" +
                 ", column_type='" + column_type + '\'' +
                 ", pr_key=" + pr_key +
                 ", create_date=" + create_date +
                 ", update_date=" + update_date +
                 '}';
        }



        public Boolean getPr_key() {
                return pr_key;
        }

        public void setPr_key(Boolean pr_key) {
                this.pr_key = pr_key;
        }

        public FkeyMeta getFkey() {
                return fkey;
        }

        public void setFkey(FkeyMeta fkey) {
                this.fkey = fkey;
        }

        public String getColumn_type() {
                return column_type;
        }

        public void setColumn_type(String column_type) {
                this.column_type = column_type;
        }

        public TableMeta getSomeTable() {
                return someTable;
        }

        public void setSomeTable(TableMeta table) {
                this.someTable = table;
        }

        public LocalDateTime getCreate_date() {
                return create_date;
        }

        public LocalDateTime getUpdate_date() {
                return update_date;
        }

        public void setCreate_date(LocalDateTime create_date) {
                this.create_date = create_date;
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
