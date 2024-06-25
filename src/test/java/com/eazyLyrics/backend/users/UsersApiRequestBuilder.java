package com.eazyLyrics.backend.users;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class UsersApiRequestBuilder {

    private final MockMvc mockMvc;

    public UsersApiRequestBuilder(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ResultActions create(String requestBody) throws Exception {
        return mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );
    }


    public ResultActions findAll() throws Exception {
        return mockMvc.perform(get("/api/users"));
    }

    public ResultActions deleteSong(String requestBody) throws Exception {
        return mockMvc.perform(delete("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
    }
}
