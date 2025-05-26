package org.esig.recrutamento.tarefasApp.enums;

public enum Status {
    EM_ANDAMENTO("Em Andamento"),
    CONCLUIDA("Concluída");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
