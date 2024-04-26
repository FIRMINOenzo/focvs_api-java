package com.api.focvs.focvs_api.dtos.auth;

public record RegisterRequestDTO(String email, String password, String name, String imageUrl) {
}
