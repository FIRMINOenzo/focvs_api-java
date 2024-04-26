package com.api.focvs.focvs_api.services.user;

import com.api.focvs.focvs_api.domain.user.User;
import com.api.focvs.focvs_api.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("basicUserService")
public class UserService implements IUserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(String id) {
        return this.userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }
}
