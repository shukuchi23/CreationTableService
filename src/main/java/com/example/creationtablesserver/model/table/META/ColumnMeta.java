package com.example.creationtablesserver.model.table.META;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    //        @EmbeddedId
//        private ColumnId column_id;
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
    @JoinColumns({
            @JoinColumn(name = "table_id", referencedColumnName = "table_id"),
            @JoinColumn(name = "project_id", referencedColumnName = "project_id"),
            @JoinColumn(name = "owner_id", referencedColumnName = "owner_id"),
    })
//        @ManyToOne
    private TableMeta coolTable;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_id")
    private FkeyMeta fkey;


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

//        public ColumnId getColumn_pk() {
//                return column_pk;
//        }

//        public void setColumn_pk(ColumnId column_pk) {
//                this.column_pk = column_pk;
//        }


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

    public ColumnMeta(String column_name, String column_type, Boolean pr_key) {
        this.column_name = column_name;
        this.column_type = column_type;
        this.pr_key = pr_key;
    }

    public ColumnMeta(String column_name, String column_type, TableMeta coolTable) {
        this.column_name = column_name;
        this.column_type = column_type;
        this.coolTable = coolTable;
    }
}
