package com.challenges.cadastrotarefas.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StatusEnumConverter implements AttributeConverter<StatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(StatusEnum status) {
        return status == null ? null : status.getCodigo();
    }

    @Override
    public StatusEnum convertToEntityAttribute(String codigo) {
        return codigo == null ? null : StatusEnum.fromCodigo(codigo);
    }
}
