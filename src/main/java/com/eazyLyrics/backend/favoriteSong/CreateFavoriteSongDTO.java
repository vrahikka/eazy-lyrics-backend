package com.eazyLyrics.backend.favoriteSong;

import jakarta.validation.constraints.NotBlank;

public class CreateFavoriteSongDTO {
    @NotBlank
    private Integer songId;
    @NotBlank
    private String email;
    @NotBlank
    private String title;
    @NotBlank
    private String artistName;
    @NotBlank
    private String thumbnailUrl;

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString() {
        return "CreateFavoriteSongDTO{" +
                "songId=" + songId +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", artistName='" + artistName + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }
}
