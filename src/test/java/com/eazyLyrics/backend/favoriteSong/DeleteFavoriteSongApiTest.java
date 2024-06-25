package com.eazyLyrics.backend.favoriteSong;

import org.assertj.db.type.Table;
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

import javax.sql.DataSource;

import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integrationTest")
@Sql({
        "/db/clean-favorite-song-database.sql",
        "/db/init-favorite-songs.sql"
})
@WithMockUser("test@test.com")
public class DeleteFavoriteSongApiTest {
    private final Integer initialRowCount = 3;
    private final FavoriteSongApiRequestBuilder apiRequestBuilder;
    private final Table favoriteSongsTable;

    @Autowired
    public DeleteFavoriteSongApiTest(MockMvc mockMvc, DataSource dataSource) {
        this.apiRequestBuilder = new FavoriteSongApiRequestBuilder(mockMvc);
        this.favoriteSongsTable = new Table(dataSource, "favorite_song");
    }

    @Nested
    @DisplayName("Valid input data and song exists")
    class WhenInputDataIsValidAndSongExists {

        private final String REQUEST_BODY = """
                {
                    "songId": 123
                }
                """;

        @Test
        @DisplayName("Should return the HTTP status code no content")
        void shouldReturnHttpStatusCodeNoContent() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY)
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Should remove one item from table")
        void shouldRemoveItemFromTable() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY);
            assertThat(favoriteSongsTable).hasNumberOfRows(initialRowCount - 1);
        }
    }

    @Nested
    @DisplayName("Valid input data and song does not exists")
    class WhenInputDataIsValidSongDoesNotExist {

        private final String REQUEST_BODY = """
                {
                    "songId": 666
                }
                """;

        @Test
        @DisplayName("Should return the HTTP status not found")
        void shouldReturnHttpStatusCodeNotFound() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY)
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return the error message")
        void shouldReturnErrorMessage() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY)
                    .andExpect(jsonPath("$.errorMessage").exists());
        }

        @Test
        @DisplayName("Should not remove any items from table")
        void shouldNotRemoveItemFromTable() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY);
            assertThat(favoriteSongsTable).hasNumberOfRows(initialRowCount);
        }
    }

    @Nested
    @DisplayName("Valid input data and song exists but is for different user")
    class WhenInputDataIsValidSongDoesExistWrongUser {

        private final String REQUEST_BODY = """
                {
                    "songId": 456
                }
                """;

        @Test
        @DisplayName("Should return the HTTP status not found")
        void shouldReturnHttpStatusCodeNotFound() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY)
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return the error message")
        void shouldReturnErrorMessage() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY)
                    .andExpect(jsonPath("$.errorMessage").exists());
        }

        @Test
        @DisplayName("Should not remove any items from table")
        void shouldNotRemoveItemFromTable() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY);
            assertThat(favoriteSongsTable).hasNumberOfRows(initialRowCount);
        }
    }

    @Nested
    @DisplayName("Invalid input data")
    class WhenInputDataIsInvalid {

        private final String REQUEST_BODY = """
                {
                }
                """;

        @Test
        @DisplayName("Should return the HTTP status code bad request")
        void shouldReturnHttpStatusCodeBadRequest() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY)
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return an API error as JSON")
        void shouldReturnAPIErrorAsJSON() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should return API error with correct error code")
        void shouldReturnApiErrorWithErrorCode() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY)
                    .andExpect(jsonPath("$.errorCode", equalTo("VALIDATION_FAILED")));
        }

        @Test
        @DisplayName("Should return validation error")
        void shouldReturnOneValidationError() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY)
                    .andExpect(jsonPath("$.validationErrors", hasSize(1)));
        }

        @Test
        @DisplayName("Should return validation error about empty songId")
        void shouldReturnValidationErrorAboutEmptySongId() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY)
                    .andExpect(jsonPath(
                            "$.validationErrors[?(@.field == 'songId')].errorCode",
                            contains("NotNull")
                    ));
        }

        @Test
        @DisplayName("Shouldn't delete a song from the database")
        void shouldNotInsertNewTodoItemIntoDatabase() throws Exception {
            apiRequestBuilder.deleteSong(REQUEST_BODY);
            assertThat(favoriteSongsTable).hasNumberOfRows(initialRowCount);
        }
    }
}
