package com.example.creationtablesserver.model.table.META;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PreDestroy;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "column_meta", schema = "tech")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ColumnMeta implements Serializable {
    @Id
    @SequenceGenerator(name = "column_id_seq",
            schema = "tech", sequenceName = "column_id_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "column_id_seq")
    private Long column_id;
    private String column_name;

    @Column(name = "column_type")
    private String column_type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_id")
    private TableMeta coolTable;

    @OneToOne(mappedBy = "owner"
            ,fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private FkeyMeta fkey;


    public void setReference(FkeyMeta fkey){
        if (fkey == null){
            if (this.fkey != null) {
                this.fkey.setOwner(null);
            }
        }
        else
            fkey.setOwner(this);
        this.fkey=fkey;
    }

    private Boolean pr_key;

    private LocalDateTime create_date;

    private LocalDateTime update_date;

    @PrePersist
    public void toCreate() {
        setCreate_date(LocalDateTime.now());
    }

    @PreUpdate
    public void toUpdate() {
        setUpdate_date(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnMeta that = (ColumnMeta) o;
        if (column_id == null)
            return false;
        else
            return (column_id.equals(that.column_id));
    }

    @Override
    public int hashCode() {
        return Objects.hash(column_id);
    }

    @Override
    public String toString() {
        return "\nColumnMeta{" +
                ", column_type='" + column_type + '\'' +
                ", pr_key=" + pr_key +
                ", create_date=" + create_date +
                ", update_date=" + update_date +
                '}';
    }

    public ColumnMeta() {
    }

}
