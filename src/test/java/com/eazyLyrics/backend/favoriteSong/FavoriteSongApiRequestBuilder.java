package com.eazyLyrics.backend.favoriteSong;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class FavoriteSongApiRequestBuilder {

    private final MockMvc mockMvc;

    public FavoriteSongApiRequestBuilder(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ResultActions create(String requestBody) throws Exception {
        return mockMvc.perform(post("/api/favorite-song")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );
    }


    public ResultActions findAll() throws Exception {
        return mockMvc.perform(get("/api/favorite-song"));
    }

    public ResultActions deleteSong(String requestBody) throws Exception {
        return mockMvc.perform(delete("/api/favorite-song")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
    }
}
