package com.api.focvs.focvs_api.infra.security;

import com.api.focvs.focvs_api.domain.account.Account;
import com.api.focvs.focvs_api.infra.security.token.ITokenService;
import com.api.focvs.focvs_api.services.account.IAccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final ITokenService tokenService;
    private final IAccountService accountService;

    public SecurityFilter(
            @Qualifier("jwtTokenService") ITokenService tokenService,
            @Qualifier("basicAccountService") IAccountService accountService
    ) {
        this.tokenService = tokenService;
        this.accountService = accountService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.recoverToken(request);

        var userAccountDTO = this.tokenService.validateToken(token);

        if (userAccountDTO != null) {
            Account account = accountService.findByEmail(userAccountDTO.getEmail()).orElseThrow(() -> new RuntimeException("Account not found."));

            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            var authentication = new UsernamePasswordAuthenticationToken(account, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }
}
