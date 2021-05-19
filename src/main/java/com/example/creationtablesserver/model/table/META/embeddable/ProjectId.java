package com.example.creationtablesserver.model.table.META.embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ProjectId implements Serializable {
    @Column(name = "project_id")
    private Long project_id;

    @Column(name = "owner_id")
    private Long owner_id;

    public ProjectId(){}
}
