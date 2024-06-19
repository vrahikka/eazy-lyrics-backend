package com.eazyLyrics.backend.favoriteSong;

import com.eazyLyrics.backend.web.SongNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteSongService {

    private final FavoriteSongRepository repository;

    public FavoriteSongService(FavoriteSongRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public FavoriteSong create(CreateFavoriteSongDTO input) {
        return repository.create(input);
    }

    @Transactional(readOnly = true)
    public List<FavoriteSong> getAll() {
        return repository.getAll();
    }

    @Transactional
    public void delete(Integer songId, String email) {

        // Check if the song exists
        if (getAll().stream().anyMatch(song -> song.getSongId().equals(songId) && song.getEmail().equals(email))) {
            repository.delete(songId, email);
        } else {
            throw new SongNotFoundException("Song not found with id " + songId + " and email " + email);
        }

    }
}
