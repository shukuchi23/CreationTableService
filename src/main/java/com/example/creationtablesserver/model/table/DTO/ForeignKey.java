package com.example.creationtablesserver.model.table.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ForeignKey implements Serializable {
        private String name;
        private String key;
        private Long referenceTable;
        private Boolean multiple;       // 1 ко многим (true), либо 1 ко 1 (false)

        /*@Override
        public String toString() {
                return "ForeignKey{" +
                 "name='" + name + '\'' +
                 ", key='" + key + '\'' +
                 ", referenceTable='" + referenceTable + '\'' +
                 '}';
        }*/


}
