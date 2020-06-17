package com.example.creationtablesserver.model.META;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "fkeys", schema = "tech_test")
public class FkeyMeta implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id_fk")
        private Integer id;

        @AttributeOverrides({
         @AttributeOverride(name = "table_name", column = @Column(name = "ref_table")),
         @AttributeOverride(name = "column_name", column = @Column(name = "ref_col"))
        })
        @Embedded
        private ColumnId refColumn;

        @Override
        public String toString() {
                return "\nFkeyMeta{" +
                 "id=" + id +
                 ", refColumn=" + refColumn +
                 ", from=" + from +
                 '}';
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                FkeyMeta fkeyMeta = (FkeyMeta) o;
                return id.equals(fkeyMeta.id) &&
                 refColumn.equals(fkeyMeta.refColumn);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, refColumn);
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public ColumnMeta getFrom() {
                return from;
        }

        public void setFrom(ColumnMeta from) {
                this.from = from;
        }

        @OneToOne(mappedBy = "fkey", cascade = {CascadeType.ALL})
        private ColumnMeta from;

        public void setRefColumn(ColumnId id) {
                this.refColumn = id;
        }

        public ColumnId getRefColumn() {
                return refColumn;
        }

        public FkeyMeta() {
        }

}
