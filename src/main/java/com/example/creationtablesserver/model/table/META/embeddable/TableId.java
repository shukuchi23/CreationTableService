package com.example.creationtablesserver.model.table.META.embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode  // в Embeddable классах можно))
@Embeddable
public class TableId implements Serializable {
    ProjectId projectId;
    Long table_id;

    public TableId(){ }
}
