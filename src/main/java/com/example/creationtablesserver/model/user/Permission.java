package com.example.creationtablesserver.model.user;

public enum Permission {
    USER_CRUD_TABLE("user:crud"),
    ADMIN_CRUD_USERS("admin:crud");

    Permission(String permission) {
        this.permission = permission;
    }
    private final String permission;

    public String getPermission() {
        return permission;
    }
}
