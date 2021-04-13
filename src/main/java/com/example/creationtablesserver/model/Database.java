package com.example.creationtablesserver.model;

public enum Database {
    PSQL("psql"),
    MYSQL("mysql"),
    ORACLE("oracle");

    Database(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }

    String database;
}
