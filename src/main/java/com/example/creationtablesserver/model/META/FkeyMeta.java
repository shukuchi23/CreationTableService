package com.example.creationtablesserver.model.META;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "fkeys", schema = "tech_test")
public class FkeyMeta {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id_fk")
        private Integer id;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ref_c1")
        private ColumnMeta ref_c1;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_c2")
        private ColumnMeta id_c2;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                FkeyMeta fkeyMeta = (FkeyMeta) o;
                return id.equals(fkeyMeta.id) &&
                 ref_c1.equals(fkeyMeta.ref_c1) &&
                 id_c2.equals(fkeyMeta.id_c2);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, ref_c1, id_c2);
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public ColumnMeta getRef_c1() {
                return ref_c1;
        }

        public void setRef_c1(ColumnMeta ref_c1) {
                this.ref_c1 = ref_c1;
        }

        public ColumnMeta getId_c2() {
                return id_c2;
        }

        public void setId_c2(ColumnMeta id_c2) {
                this.id_c2 = id_c2;
        }

        public FkeyMeta() {}

}
