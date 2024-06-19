package com.eazyLyrics.backend.favoriteSong;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class FavoriteSongRepository {

    private final JdbcClient jdbcClient;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public FavoriteSongRepository(JdbcClient jdbcClient, NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcClient = jdbcClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    public FavoriteSong create(CreateFavoriteSongDTO input) {
        var keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("""
                        INSERT INTO favorite_song (song_id, email, title, artist_name, thumbnail_url)
                        VALUES (:song_id, :email, :title, :artist_name, :thumbnail_url)
                        RETURNING id
                        """)
                .param("song_id", input.getSongId())
                .param("email", input.getEmail())
                .param("title", input.getTitle())
                .param("artist_name", input.getArtistName())
                .param("thumbnail_url", input.getThumbnailUrl())
                .update(keyHolder);

        return jdbcClient.sql("""
                        SELECT id, song_id, email, title, artist_name, thumbnail_url
                        FROM favorite_song
                        WHERE id = :id
                        """)
                .param("id", keyHolder.getKeyAs(Long.class))
                .query(FavoriteSong.class)
                .single();

    }

    public List<FavoriteSong> getAll() {
        return jdbcTemplate.query(
                "SELECT id, song_id, email, title, artist_name, thumbnail_url FROM favorite_song ORDER BY artist_name ASC",
                new BeanPropertyRowMapper(FavoriteSong.class));
    }

    public void delete(Integer songId, String email) {
        jdbcClient.sql("""
                DELETE FROM favorite_song WHERE song_id = :song_id AND email = :email
                """)
                .param("song_id", songId)
                .param("email", email)
                .update();

    }
}
