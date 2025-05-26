package org.esig.recrutamento.tarefasApp.enums;

public enum Prioridade {
    ALTA("Alta"),
    MEDIA("Média"),
    BAIXA("Baixa");

    private final String label;

    Prioridade(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
