package com.example.creationtablesserver.model;

public enum Database {
    PSQL("PostgreSQL"),
    MYSQL("MySQL"),
    ORACLE("Oracle");

    Database(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }

    String database;
}
