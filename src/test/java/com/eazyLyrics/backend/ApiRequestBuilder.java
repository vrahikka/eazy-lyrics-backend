package com.eazyLyrics.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class ApiRequestBuilder {

    private final MockMvc mockMvc;

    public ApiRequestBuilder(MockMvc mockMvc) {
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
