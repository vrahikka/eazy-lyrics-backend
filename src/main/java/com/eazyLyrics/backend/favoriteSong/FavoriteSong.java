package com.eazyLyrics.backend.favoriteSong;

import lombok.Data;

@Data
public class FavoriteSong {
    private  Integer songId;
    private  String email;
    private  String title;
    private  String artistName;
    private  String thumbnailUrl;
}
