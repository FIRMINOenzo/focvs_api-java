package com.api.focvs.focvs_api.services.user;

import com.api.focvs.focvs_api.domain.user.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findById(String id);

    User save(User user);
}
