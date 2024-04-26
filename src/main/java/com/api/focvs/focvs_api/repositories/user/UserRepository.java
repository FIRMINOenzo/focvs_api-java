package com.api.focvs.focvs_api.repositories.user;

import com.api.focvs.focvs_api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
