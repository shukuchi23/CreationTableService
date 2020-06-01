package com.example.creationtablesserver.model.META;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "column_meta", schema = "tech_test")
public class ColumnMeta implements Serializable {
        @Id
        @GeneratedValue
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
                 ", foreignKey=" + foreignKey +
                 '}';
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ColumnMeta that = (ColumnMeta) o;
                return id.equals(that.id) &&
                 column_name.equals(that.column_name) &&
                 column_type.equals(that.column_type) &&
                 pr_key.equals(that.pr_key) &&
                 create_date.equals(that.create_date) &&
                 update_date.equals(that.update_date) &&
                 table.equals(that.table) &&
                 Objects.equals(foreignKey, that.foreignKey);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, column_name, column_type, pr_key, create_date, update_date, table, foreignKey);
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
        @JoinTable(
                 name = "table_column",
                 joinColumns = @JoinColumn(name = "column_id"),
                 inverseJoinColumns = @JoinColumn(name = "table_name"))
        private TableMeta table;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fk_key")
        private ColumnMeta foreignKey;

        public ColumnMeta getForeignKey() {
                return foreignKey;
        }

        public void setForeignKey(ColumnMeta foreignKey) {
                this.foreignKey = foreignKey;
        }

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
