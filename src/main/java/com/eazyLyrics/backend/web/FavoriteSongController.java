package com.eazyLyrics.backend.web;

import com.eazyLyrics.backend.favoriteSong.CreateFavoriteSongDTO;
import com.eazyLyrics.backend.favoriteSong.FavoriteSong;
import com.eazyLyrics.backend.favoriteSong.FavoriteSongService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FavoriteSongController {

    private final FavoriteSongService service;

    public FavoriteSongController(FavoriteSongService service) {
        this.service = service;
    }


    @PostMapping("/api/favorite-song")
    @ResponseStatus(HttpStatus.CREATED)
    public FavoriteSong create(@RequestBody CreateFavoriteSongDTO input) {
        System.out.println("POST /api/favorite-song: " + input);
        return service.create(input);
    }

    @GetMapping("/api/favorite-song")
    public List<FavoriteSong> getAll() {
        var favorites = service.getAll();
        System.out.println("GET /api/favorite-song: Favorite songs sent " + favorites.size());
        return  service.getAll();
    }
}
