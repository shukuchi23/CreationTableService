package com.example.creationtablesserver.model.table.META;

import com.example.creationtablesserver.model.project.Project;
import com.example.creationtablesserver.model.table.META.embeddable.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "table_meta", schema = "tech")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TableMeta implements Serializable {
    //        -- FIELD---
        /*@Id
        @SequenceGenerator(name = "table_id_seq",
                schema = "tech", sequenceName = "table_id_seq", allocationSize = 1
        )
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_id_seq")
        private Long table_id;*/

    @EmbeddedId
    private TableId table_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumns({
            @JoinColumn(name = "project_id", referencedColumnName = "project_id"),
            @JoinColumn(name = "owner_id", referencedColumnName = "owner_id"),
    })
    private Project project;

    private String table_name;

    private LocalDateTime create_date;
    private LocalDateTime update_date;

    @OneToMany(mappedBy = "coolTable",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ColumnMeta> columns = new LinkedList<>();

    public ColumnMeta findColumnByName(String name) {
        return columns.stream().filter(c -> c.getColumn_name().equals(name)).findFirst().get();
    }
    public void addColumn(ColumnMeta columnMeta) {
        columns.add(columnMeta);
        columnMeta.setCoolTable(this);
    }

    public void removeColumn(ColumnMeta columnMeta) {
        columns.add(columnMeta);
        columnMeta.setCoolTable(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableMeta that = (TableMeta) o;
        if (table_id == null)
            return false;
        else
            return (table_id.equals(that.table_id));
    }

    @Override
    public int hashCode() {
        return Objects.hash(table_id);
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
                "project_id='" + table_id.getProjectId().getProject_id() + '\'' +
                "table_id='" + table_id.getTable_id() + '\'' +
                "table_name='" + table_name + '\'' +
                ", create_date=" + create_date +
                ", update_date=" + update_date +
                ", columns=" + columns +
                '}';
    }
}
