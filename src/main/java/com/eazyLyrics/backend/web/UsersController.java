package com.eazyLyrics.backend.web;

import com.eazyLyrics.backend.user.CreateUserDTO;
import com.eazyLyrics.backend.user.DeleteUserDTO;
import com.eazyLyrics.backend.user.User;
import com.eazyLyrics.backend.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService service;

    @GetMapping
    public List<User> getAll() {
        var users = service.getAll();
        System.out.println("GET /api/users: Users sent " + users.size());
        return users;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid CreateUserDTO input) {
        System.out.println("POST /api/users: " + input);
        return service.create(input);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Valid DeleteUserDTO input) {
        System.out.println("DELETE /api/users: " + input);
        service.delete(input.getEmail());
    }
}
