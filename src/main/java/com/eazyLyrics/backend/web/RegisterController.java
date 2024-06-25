package com.eazyLyrics.backend.web;

import com.eazyLyrics.backend.user.CreateUserDTO;
import com.eazyLyrics.backend.user.User;
import com.eazyLyrics.backend.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid CreateUserDTO input) {
        System.out.println("POST /api/register: " + input);
        return service.create(input);
    }
}
