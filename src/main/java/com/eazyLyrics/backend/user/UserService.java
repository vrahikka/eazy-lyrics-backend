package com.eazyLyrics.backend.user;

import com.eazyLyrics.backend.web.SongNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return repository.findAll();
    }

    @Transactional
    public void create(CreateUserDTO user) {
        var newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(newUser);
    }

    @Transactional
    public void delete(String email) {
        if(getAll().stream().anyMatch(user -> user.getEmail().equals(email))) {
            repository.deleteByEmail(email);
        } else {
            throw new SongNotFoundException("User not found with email " + email);
        }
    }

}
