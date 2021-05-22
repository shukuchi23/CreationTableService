package com.example.creationtablesserver.enums;

public enum Database {
    PSQL("PostgreSQL"),
    MYSQL("MySQL"),
    ORACLE("Oracle");

    Database(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    String dbName;
}
