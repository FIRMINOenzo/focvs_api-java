package com.api.focvs.focvs_api.dtos.error;

import org.springframework.http.HttpStatus;

public record ErrorMessageDTO(HttpStatus status, Boolean error, String message) {
}
