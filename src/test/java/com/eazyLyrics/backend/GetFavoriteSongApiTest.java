package com.eazyLyrics.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integrationTest")
class GetFavoriteSongApiTest {

    private final ApiRequestBuilder apiRequestBuilder;

    @Autowired
    public GetFavoriteSongApiTest(MockMvc mockMvc) {
        this.apiRequestBuilder = new ApiRequestBuilder(mockMvc);
    }

    @Nested
    @DisplayName("When no todo items is found")
    @Sql({
            "/db/clean-database.sql"
    })
    class WhenNoTodoItemsIsFound {

        @Test
        @DisplayName("Should return HTTP status code ok")
        void shouldReturnHttpStatusCodeOk() throws Exception {
            apiRequestBuilder.findAll()
                    .andExpect(status().isOk());
        }
    }

}
