package com.api.focvs.focvs_api.infra.security.token;

import com.api.focvs.focvs_api.dtos.user.UserAccountDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ITokenService {
    String generateToken(UserAccountDTO userAccountDTO) throws Exception;

    UserAccountDTO validateToken(String token) throws JsonProcessingException;
}
