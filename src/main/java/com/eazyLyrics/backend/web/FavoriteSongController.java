package com.eazyLyrics.backend.web;

import com.eazyLyrics.backend.favoriteSong.CreateFavoriteSongDTO;
import com.eazyLyrics.backend.favoriteSong.DeleteFavoriteSongDTO;
import com.eazyLyrics.backend.favoriteSong.FavoriteSong;
import com.eazyLyrics.backend.favoriteSong.FavoriteSongService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/favorite-song")
public class FavoriteSongController {

    private final FavoriteSongService service;

    public FavoriteSongController(FavoriteSongService service) {
        this.service = service;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavoriteSong create(@RequestBody @Valid CreateFavoriteSongDTO input) {
        System.out.println("POST /api/favorite-song: " + input);
        return service.create(input);
    }

    @GetMapping
    public List<FavoriteSong> getAll() {
        var favorites = service.getAll();

        System.out.println("GET /api/favorite-song: Favorite songs sent " + favorites.size());
        return  service.getAll();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Valid DeleteFavoriteSongDTO input) {
        System.out.println("DELETE /api/favorite-song: " + input);
        service.delete(input.getSongId(), input.getEmail());
    }
}
