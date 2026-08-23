package com.challenges.cadastrotarefas.auth;

public record LoginResponse(String accessToken, String tokenType, long expiresIn) {
}
