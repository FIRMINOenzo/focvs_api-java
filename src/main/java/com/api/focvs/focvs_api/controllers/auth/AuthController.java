package com.api.focvs.focvs_api.controllers.auth;

import com.api.focvs.focvs_api.domain.account.Account;
import com.api.focvs.focvs_api.domain.user.User;
import com.api.focvs.focvs_api.dtos.auth.AuthResponseDTO;
import com.api.focvs.focvs_api.dtos.auth.LoginRequestDTO;
import com.api.focvs.focvs_api.dtos.auth.RegisterRequestDTO;
import com.api.focvs.focvs_api.dtos.user.UserAccountDTO;
import com.api.focvs.focvs_api.infra.security.password.IPasswordService;
import com.api.focvs.focvs_api.infra.security.token.ITokenService;
import com.api.focvs.focvs_api.services.account.IAccountService;
import com.api.focvs.focvs_api.services.user.IUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController implements IAuthController {
    private final IAccountService accountService;
    private final IUserService userService;
    private final IPasswordService passwordService;
    private final ITokenService tokenService;

    public AuthController(
            @Qualifier("basicAccountService") IAccountService accountService,
            @Qualifier("basicUserService") IUserService userService,
            @Qualifier("bCryptPasswordService") IPasswordService passwordService,
            @Qualifier("jwtTokenService") ITokenService tokenService
    ) {
        this.accountService = accountService;
        this.userService = userService;
        this.passwordService = passwordService;
        this.tokenService = tokenService;
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception {
        Account account = this.accountService.findByEmail(loginRequestDTO.email()).orElseThrow(() -> new RuntimeException("Account not found."));

        if (!this.passwordService.matches(loginRequestDTO.password(), account.getPassword())) {
            throw new RuntimeException("Password does not match.");
        }

        User user = this.userService
                .findById(account.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found."));

        String token = this.tokenService.generateToken(
                new UserAccountDTO(
                        account.getId(),
                        account.getEmail(),
                        user.getName(),
                        user.getImageUrl()
                )
        );

        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDTO(token));
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO) throws Exception {
        if (this.accountService.findByEmail(registerRequestDTO.email()).isPresent()) {
            throw new RuntimeException("Email already in use.");
        }

        User user = new User(registerRequestDTO);

        User savedUser = this.userService.save(user);

        Account account = new Account();
        account.setEmail(registerRequestDTO.email());
        account.setPassword(this.passwordService.encode(registerRequestDTO.password()));
        account.setUser(savedUser);

        Account savedAccount = this.accountService.save(account);

        String token = this.tokenService.generateToken(
                new UserAccountDTO(
                        savedAccount.getId(),
                        savedAccount.getEmail(),
                        savedUser.getName(),
                        savedUser.getImageUrl()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDTO(token));
    }
}
