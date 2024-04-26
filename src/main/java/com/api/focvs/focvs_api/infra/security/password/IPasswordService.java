package com.api.focvs.focvs_api.infra.security.password;

public interface IPasswordService {
    String encode (String password);

    Boolean matches (String rawPassword, String encodedPassword);
}
