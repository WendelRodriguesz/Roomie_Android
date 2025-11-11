package com.project.roomie.core.model.enums;

public enum Role {

    INTERESSADO("ADMIN"),
    OFERTANTE("RECEPCIONISTA");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}