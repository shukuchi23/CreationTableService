package com.example.creationtablesserver.model.table.META;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "fkeys", schema = "tech")
public class FkeyMeta implements Serializable {

    /*@SequenceGenerator(name = "fkey_id_seq",
            schema = "tech", sequenceName = "fkey_id_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fkey_id_seq")*/
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "column_id")
    private ColumnMeta owner;
    private Boolean multiply;

    @OneToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "ref_column")
    private ColumnMeta refColumn;

    /*@OneToMany(mappedBy = "fkey")
    private List<ColumnMeta> columnMetaList = new LinkedList<>();*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FkeyMeta that = (FkeyMeta) o;
        if (id == null)
            return false;
        else
            return (id.equals(that.id));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public FkeyMeta(Boolean multiply, ColumnMeta refColumn) {
        this.multiply = multiply;
        this.refColumn = refColumn;
    }

    public FkeyMeta() {
    }

}
