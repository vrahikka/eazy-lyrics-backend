package com.eazyLyrics.backend.favoriteSong;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteFavoriteSongDTO {
    @NotNull
    private Integer songId;
}
