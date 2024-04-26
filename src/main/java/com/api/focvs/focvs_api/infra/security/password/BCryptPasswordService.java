package com.api.focvs.focvs_api.infra.security.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("bCryptPasswordService")
public class BCryptPasswordService implements IPasswordService{
    private final BCryptPasswordEncoder passwordService;

    public BCryptPasswordService(BCryptPasswordEncoder bCryptPasswordService) {
        this.passwordService = bCryptPasswordService;
    }
    @Override
    public String encode(String password) {
        return this.passwordService.encode(password);
    }

    @Override
    public Boolean matches(String rawPassword, String encodedPassword) {
        return this.passwordService.matches(rawPassword, encodedPassword);
    }
}
