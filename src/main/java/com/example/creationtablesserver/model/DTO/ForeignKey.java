package com.example.creationtablesserver.model.DTO;

import java.io.Serializable;

public class ForeignKey implements Serializable {
        private String name;
        private String key;
        private String referenceTable;

        /*@Override
        public String toString() {
                return "ForeignKey{" +
                 "name='" + name + '\'' +
                 ", key='" + key + '\'' +
                 ", referenceTable='" + referenceTable + '\'' +
                 '}';
        }*/

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getKey() {
                return key;
        }

        public void setKey(String key) {
                this.key = key;
        }

        public String getReferenceTable() {
                return referenceTable;
        }

        public void setReferenceTable(String referenceTable) {
                this.referenceTable = referenceTable;
        }
}
