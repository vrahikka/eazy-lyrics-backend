package com.eazyLyrics.backend.favoriteSong;

public class FavoriteSong {
    private  Integer songId;
    private  String email;
    private  String title;
    private  String artistName;
    private  String thumbnailUrl;

    public FavoriteSong() {
        this.songId = null;
        this.email = null;
        this.title = null;
        this.artistName = null;
        this.thumbnailUrl = null;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getSongId() {
        return songId;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }


}
