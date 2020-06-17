package com.example.creationtablesserver.model.DTO;
import java.io.Serializable;
import java.util.Objects;

public class PrimaryKey implements Serializable {
        private String key;

        public PrimaryKey() {}
        public PrimaryKey(String key) {this.key=key;}
        public String getKey() {
                return key;
        }

        public void setKey(String key) {
                this.key = key;
        }

        @Override
        public boolean equals(Object o) {
                if (o != null && o instanceof String)
                        return key.equals((String) o);
                if (o == null || getClass() != o.getClass()) return false;
                PrimaryKey that = (PrimaryKey) o;
                return key.equals(that.key);
        }

        @Override
        public int hashCode() {
                return Objects.hash(key);
        }
}
