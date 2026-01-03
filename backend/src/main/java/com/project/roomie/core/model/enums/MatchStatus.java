package com.project.roomie.core.model.enums;

public enum MatchStatus {

    PENDENTE("PENDENTE"),
    ACEITO("ACEITO"),
    RECUSADO("RECUSADO");

    private final String match_status;

    MatchStatus(String match_status) {
        this.match_status = match_status;
    }

    public String getMatch_status() {
        return match_status;
    }
}