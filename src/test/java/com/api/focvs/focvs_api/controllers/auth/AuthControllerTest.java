package com.api.focvs.focvs_api.controllers.auth;

import com.api.focvs.focvs_api.dtos.auth.AuthResponseDTO;
import com.api.focvs.focvs_api.dtos.auth.LoginRequestDTO;
import com.api.focvs.focvs_api.dtos.auth.RegisterRequestDTO;
import com.api.focvs.focvs_api.dtos.error.ErrorMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    @DisplayName("Should register a new user with success.")
    void registerANewUserWithSuccess() throws Exception {
        String email = "test@gmail.com";
        String password = "test123";
        String name = "Test user";
        String imageUrl = "https://www.image.com/exemple";

        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(email, password, name, imageUrl);

        ResponseEntity<String> response = this.restTemplate.postForEntity("/auth/register", registerRequestDTO, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        AuthResponseDTO authResponseDTO = this.objectMapper.readValue(response.getBody(), AuthResponseDTO.class);

        assertThat(authResponseDTO.token()).isNotEmpty();
    }

    @Test
    @Order(2)
    @DisplayName("Should receive error by email already registered.")
    void registerWithAlreadyUsedEmailError() throws Exception {
        String email = "test@gmail.com";
        String password = "test123";
        String name = "Test user";
        String imageUrl = "https://www.image.com/exemple";

        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO(email, password, name, imageUrl);

        ResponseEntity<String> response = this.restTemplate.postForEntity("/auth/register", registerRequestDTO, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ErrorMessageDTO errorMessageDTO = this.objectMapper.readValue(response.getBody(), ErrorMessageDTO.class);

        assertThat(errorMessageDTO.error()).isTrue();
        assertThat(errorMessageDTO.message()).isEqualTo("Email already in use.");
    }

    @Test
    @Order(3)
    @DisplayName("Should receive error by account not found")
    void loginWithAccountNotFoundError() throws Exception {
        String email = "nonexistent@gmail.com";
        String password = "pass123";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);

        ResponseEntity<String> response = this.restTemplate.postForEntity("/auth/login", loginRequestDTO, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ErrorMessageDTO errorMessageDTO = this.objectMapper.readValue(response.getBody(), ErrorMessageDTO.class);

        assertThat(errorMessageDTO.error()).isTrue();
        assertThat(errorMessageDTO.message()).isEqualTo("Account not found.");
    }

    @Test
    @Order(4)
    @DisplayName("Should receive error by wrong password.")
    void loginWithWrongPasswordError() throws Exception {
        String email = "test@gmail.com";
        String password = "wrongPass";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);

        ResponseEntity<String> response = this.restTemplate.postForEntity("/auth/login", loginRequestDTO, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        ErrorMessageDTO errorMessageDTO = this.objectMapper.readValue(response.getBody(), ErrorMessageDTO.class);

        assertThat(errorMessageDTO.error()).isTrue();
        assertThat(errorMessageDTO.message()).isEqualTo("Password does not match.");
    }

    @Test
    @Order(5)
    @DisplayName("Should perform login with success.")
    void loginWithSuccess() throws Exception {
        String email = "test@gmail.com";
        String password = "test123";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);

        ResponseEntity<String> response = this.restTemplate.postForEntity("/auth/login", loginRequestDTO, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        AuthResponseDTO authResponseDTO = this.objectMapper.readValue(response.getBody(), AuthResponseDTO.class);

        assertThat(authResponseDTO.token()).isNotEmpty();
    }
}
