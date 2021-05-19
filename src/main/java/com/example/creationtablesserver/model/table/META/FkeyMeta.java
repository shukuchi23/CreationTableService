package com.example.creationtablesserver.model.table.META;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "fkeys", schema = "tech")
public class FkeyMeta implements Serializable {
    @Id
    @SequenceGenerator(name = "fkey_id_seq",
            schema = "tech", sequenceName = "fkey_id_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fkey_id_seq")
    @Column(name = "fk_id")
    private Integer id;

    private Boolean multiply;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ref_column")
    private ColumnMeta refColumn;

/*  Мб можно без неё?

    @OneToOne(mappedBy = "fkey", optional = false)
    private ColumnMeta from;*/

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
