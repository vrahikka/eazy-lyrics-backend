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
    public FavoriteSong create(CreateFavoriteSongDTO input, String email) {
        return repository.create(input, email);
    }

    @Transactional(readOnly = true)
    public List<FavoriteSong> getAll(String email) {
        return repository.getAll(email);
    }

    @Transactional
    public void delete(Integer songId, String email) {
        // Check if the song exists
        if (getAll(email).stream().anyMatch(song -> song.getSongId().equals(songId) && song.getEmail().equals(email))) {
            repository.delete(songId, email);
        } else {
            throw new SongNotFoundException("Song not found with id " + songId + " and email " + email);
        }

    }
}
