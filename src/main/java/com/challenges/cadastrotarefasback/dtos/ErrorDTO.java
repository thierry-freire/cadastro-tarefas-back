package com.challenges.cadastrotarefasback.dtos;

import java.time.LocalDateTime;

public record ErrorDTO(LocalDateTime timestamp,
                       Integer status,
                       String error,
                       String message,
                       String path) {
}
