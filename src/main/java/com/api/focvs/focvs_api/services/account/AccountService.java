package com.api.focvs.focvs_api.services.account;

import com.api.focvs.focvs_api.domain.account.Account;
import com.api.focvs.focvs_api.repositories.account.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("basicAccountService")
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;

    public AccountService (AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findByEmail(String email) {
       return this.accountRepository.findByEmail(email);
    }

    @Override
    public Account save(Account account) {
        return this.accountRepository.save(account);
    }
}
