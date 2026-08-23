package com.challenges.cadastrotarefas.enums;

public enum StatusEnum {
    PENDENTE("P"),
    EM_ANDAMENTO("A"),
    CONCLUIDA("C");

    private final String codigo;

    StatusEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static StatusEnum fromCodigo(String codigo) {
        for (StatusEnum tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + codigo);
    }
}
