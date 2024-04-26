package com.api.focvs.focvs_api.services.account;

import com.api.focvs.focvs_api.domain.account.Account;

import java.util.Optional;

public interface IAccountService {
    Optional<Account> findByEmail (String email);

    Account save (Account account);
}
