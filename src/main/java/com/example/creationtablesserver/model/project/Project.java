package com.example.creationtablesserver.model.project;

import com.example.creationtablesserver.enums.Database;
import com.example.creationtablesserver.enums.DatabaseConverter;
import com.example.creationtablesserver.model.table.META.TableMeta;
import com.example.creationtablesserver.model.user.AuthorityUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "project_meta", schema = "tech")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Project implements Serializable {

    @Id
    @SequenceGenerator(name = "project_id_seq",
            schema = "tech", sequenceName = "project_id_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_seq")
    private Long projectId;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private AuthorityUser owner;

    @Column(name = "project_name")
    @NonNull
    private String projectName;

    @OneToMany(mappedBy = "project",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TableMeta> tables = new LinkedList<>();

    @NonNull
    @Convert(converter = DatabaseConverter.class)
    private Database database;

    public void addTable(TableMeta tableMeta) {
        tables.add(tableMeta);
        tableMeta.setProject(this);
    }
    public void removeTable(TableMeta tableMeta){
        tables.remove(tableMeta);
        tableMeta.setProject(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project that = (Project) o;
        if (projectId == null)
            return false;
        else
            return (projectId.equals(that.projectId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId);
    }

    public Project() {
        
    }
}
