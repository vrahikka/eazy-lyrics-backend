package com.eazyLyrics.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integrationTest")
@WithMockUser("test@test.com")
class GetFavoriteSongApiTest {

    private final ApiRequestBuilder apiRequestBuilder;


    public GetFavoriteSongApiTest(@Autowired MockMvc mockMvc) {
        this.apiRequestBuilder = new ApiRequestBuilder(mockMvc);
    }

    @Nested
    @DisplayName("When no todo items is found")
    @Sql({
            "/db/clean-database.sql"
    })
    class WhenNoItemsIsFound {

        @Test
        @DisplayName("Should return HTTP status code ok")
        void shouldReturnHttpStatusCodeOk() throws Exception {
            apiRequestBuilder.findAll()
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return the found todo items as JSON")
        void shouldReturnFoundTodoItemsJSON() throws Exception {
            apiRequestBuilder.findAll()
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should return an empty list")
        void shouldReturnEmptyList() throws Exception {
            apiRequestBuilder.findAll()
                    .andExpect(jsonPath("$", hasSize(0)));
        }
    }

    @Nested
    @DisplayName("When two items are found")
    @Sql({
            "/db/clean-database.sql",
            "/db/init-favorite-songs.sql"
    })
    class WhenTwoItemsAreFound {

        @Test
        @DisplayName("Should return HTTP status ok")
        void shouldReturnHttpStatusOk() throws Exception {
            apiRequestBuilder.findAll().andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return the found todo items as JSON")
        void shouldReturnFoundTodoItemsJSON() throws Exception {
            apiRequestBuilder.findAll()
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should return an two items")
        void shouldReturnTwoItems() throws Exception {
            apiRequestBuilder.findAll()
                    .andExpect(jsonPath("$", hasSize(2)));
        }

        @Test
        @DisplayName("Should return the information of the first item")
        void shouldReturnInformationOfFirstTodoItem() throws Exception {
            var test = apiRequestBuilder.findAll();
            apiRequestBuilder.findAll()
                    .andExpect(jsonPath("$[0].songId", equalTo(123)))
                    .andExpect(jsonPath("$[0].title", equalTo("Test")));
        }

        @Test
        @DisplayName("Should return the information of the second item")
        void shouldReturnInformationOfSecondTodoItem() throws Exception {
            apiRequestBuilder.findAll()
                    .andExpect(jsonPath("$[1].songId", equalTo(321)))
                    .andExpect(jsonPath("$[1].title", equalTo("Test2")));
        }
    }
}
