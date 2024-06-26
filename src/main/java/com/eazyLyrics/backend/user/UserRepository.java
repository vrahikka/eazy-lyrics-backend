package com.eazyLyrics.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByEmail(String email);
    User findByEmail(String email);
}
