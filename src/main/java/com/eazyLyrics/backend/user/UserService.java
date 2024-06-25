package com.eazyLyrics.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return repository.findAll();
    }

    @Transactional
    public User create(CreateUserDTO user) {
        var newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        return repository.save(newUser);
    }

}
