package com.api.focvs.focvs_api.infra.security.token;

import com.api.focvs.focvs_api.dtos.user.UserAccountDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service("jwtTokenService")
public class TokenService implements ITokenService{
    @Value("${api.security.jwt.token.secret}")
    private String secret;

    private final ObjectMapper objectMapper;

    public TokenService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String generateToken(UserAccountDTO userAccountDTO) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            String subject = objectMapper.writeValueAsString(userAccountDTO);

            Long tokenHoursDuration = 48L;

            return JWT.create()
                    .withIssuer("focvs-api")
                    .withSubject(subject)
                    .withExpiresAt(this.generateExpirationTime(tokenHoursDuration))
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating JWT token.");
        }
    }

    @Override
    public UserAccountDTO validateToken(String token) throws JsonProcessingException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            String subject = JWT.require(algorithm)
                    .withIssuer("focvs-api")
                    .build()
                    .verify(token)
                    .getSubject();

            return objectMapper.readValue(subject, UserAccountDTO.class);
        } catch (JWTVerificationException  exception) {
            return null;
        }
    }

    private Instant generateExpirationTime(Long hoursToAdd) {
        return LocalDateTime.now().plusHours(hoursToAdd).toInstant(ZoneOffset.of("Z"));
    }
}
