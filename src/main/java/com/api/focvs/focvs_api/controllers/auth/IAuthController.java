package com.api.focvs.focvs_api.controllers.auth;

import com.api.focvs.focvs_api.dtos.auth.AuthResponseDTO;
import com.api.focvs.focvs_api.dtos.auth.LoginRequestDTO;
import com.api.focvs.focvs_api.dtos.auth.RegisterRequestDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthController {
    ResponseEntity<AuthResponseDTO> login(LoginRequestDTO loginRequestDTO) throws Exception;

    ResponseEntity<AuthResponseDTO> register(RegisterRequestDTO registerRequestDTO) throws Exception;
}
