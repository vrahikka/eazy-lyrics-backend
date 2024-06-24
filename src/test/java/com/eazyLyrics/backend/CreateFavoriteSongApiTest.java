package com.eazyLyrics.backend;

import org.assertj.db.api.SoftAssertions;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integrationTest")
@Sql({
        "/db/clean-database.sql"
})
@WithMockUser("test@test.com")
class CreateFavoriteSongApiTest {

    private final ApiRequestBuilder apiRequestBuilder;
    private final Table favoriteSongsTable;

    @Autowired
    public CreateFavoriteSongApiTest(MockMvc mockMvc, DataSource dataSource) {
        this.apiRequestBuilder = new ApiRequestBuilder(mockMvc);
        this.favoriteSongsTable = new Table(dataSource, "favorite_song");
    }

    @Nested
    @DisplayName("When input data contains empty strings")
    class WhenInputDataContainsEmptyStrings {

        private final String REQUEST_BODY = """
                {
                }
                """;

        @Test
        @DisplayName("Should return the HTTP status code bad request")
        void shouldReturnHttpStatusCodeBadRequest() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return an API error as JSON")
        void shouldReturnAPIErrorAsJSON() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should return API error with correct error code")
        void shouldReturnApiErrorWithErrorCode() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(jsonPath("$.errorCode", equalTo("VALIDATION_FAILED")));
        }

        @Test
        @DisplayName("Should return multiple validation error")
        void shouldReturnOneValidationError() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(jsonPath("$.validationErrors", hasSize(4)));
        }

        @Test
        @DisplayName("Should return validation error about empty thumbnailUrl")
        void shouldReturnValidationErrorAboutEmptyThumbnailUrl() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(jsonPath(
                            "$.validationErrors[?(@.field == 'thumbnailUrl')].errorCode",
                            contains("NotBlank")
                    ));
        }

        @Test
        @DisplayName("Should return validation error about empty artistName")
        void shouldReturnValidationErrorAboutEmptyArtistName() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(jsonPath(
                            "$.validationErrors[?(@.field == 'artistName')].errorCode",
                            contains("NotBlank")
                    ));
        }

        @Test
        @DisplayName("Should return validation error about empty title")
        void shouldReturnValidationErrorAboutEmptyTitle() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(jsonPath(
                            "$.validationErrors[?(@.field == 'title')].errorCode",
                            contains("NotBlank")
                    ));
        }

        @Test
        @DisplayName("Should return validation error about empty songId")
        void shouldReturnValidationErrorAboutEmptySongId() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(jsonPath(
                            "$.validationErrors[?(@.field == 'songId')].errorCode",
                            contains("NotNull")
                    ));
        }

        @Test
        @DisplayName("Shouldn't insert a new song into the database")
        void shouldNotInsertNewTodoItemIntoDatabase() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY);
            assertThat(favoriteSongsTable).hasNumberOfRows(0);
        }
    }

    @Nested
    @DisplayName("When input data is valid")
    class WhenInputDataIsValid {

        private final String REQUEST_BODY = """
                {
                    "songId": 123,
                    "title": "Test",
                    "artistName": "Test Testerson",
                    "thumbnailUrl": "www.test.com"
                }
                """;

        @Test
        @DisplayName("Should return the HTTP status code created")
        void shouldReturnHttpStatusCodeCreated() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("Should return the information of the created favorite song as JSON")
        void shouldReturnInformationOfCreatedTodoItemAsJSON() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should return the information of the created favorite song")
        void shouldReturnInformationOfCreatedTodoItem() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(jsonPath("$.songId", equalTo(123)))
                    .andExpect(jsonPath("$.title", equalTo("Test")))
                    .andExpect(jsonPath("$.email", equalTo("test@test.com")))
                    .andExpect(jsonPath("$.artistName", equalTo("Test Testerson")))
                    .andExpect(jsonPath("$.thumbnailUrl", equalTo("www.test.com")));
        }

        @Test
        @DisplayName("Should insert one todo item into the favoriteSongs table")
        void shouldInsertOneTodoItemIntoTodoItemTable() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY);
            assertThat(favoriteSongsTable).hasNumberOfRows(1);
        }

        @Test
        @DisplayName("Should insert the correct id into the favoriteSongs table")
        void shouldInsertCorrectIdIntoTodoItemTable() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY);
            assertThat(favoriteSongsTable)
                    .row(0)
                    .value("id")
                    .isEqualTo(1L);
        }


        @Test
        @DisplayName("Should insert correct item into the favoriteSongs table")
        void shouldInsertCorrectTodoItemIntoTodoItemTable() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY);

            var softAssertions = new SoftAssertions();

            softAssertions.assertThat(favoriteSongsTable)
                    .row(0)
                    .value("id")
                    .as("id")
                    .isEqualTo(1L);
            softAssertions.assertThat(favoriteSongsTable)
                    .row(0)
                    .value("song_id")
                    .as("songId")
                    .isEqualTo("123");
            softAssertions.assertThat(favoriteSongsTable)
                    .row(0)
                    .value("title")
                    .as("title")
                    .isEqualTo("Test");
            softAssertions.assertThat(favoriteSongsTable)
                    .row(0)
                    .value("email")
                    .as("email")
                    .isEqualTo("test@test.com");
            softAssertions.assertThat(favoriteSongsTable)
                    .row(0)
                    .value("artist_name")
                    .as("artistName")
                    .isEqualTo("Test Testerson");
            softAssertions.assertThat(favoriteSongsTable)
                    .row(0)
                    .value("thumbnail_url")
                    .as("thumbnailUrl")
                    .isEqualTo("www.test.com");

            softAssertions.assertAll();
        }
    }

    @Nested
    @DisplayName("When created song already exists")
    @Sql({
            "/db/clean-database.sql",
            "/db/init-favorite-songs.sql"
    })
    class WhenFavoriteSongAlreadyExists {

        private final String REQUEST_BODY = """
                {
                    "songId": "123",
                    "title": "Test",
                    "email": "test@test.com",
                    "artistName": "Test Testerson",
                    "thumbnailUrl": "www.test.com"
                }
                """;

        @Test
        @DisplayName("Should return the HTTP status code bad request")
        void shouldReturnHttpStatusCodeBadRequest() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return an API error as JSON")
        void shouldReturnAPIErrorAsJSON() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should return API error with correct error code")
        void shouldReturnApiErrorWithErrorCode() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(jsonPath("$.errorCode", equalTo("DATA_BASE_ERROR")));
        }

        @Test
        @DisplayName("Should return API error with message")
        void shouldReturnApiErrorWithErrorMessage() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(jsonPath("$.errorMessage").exists());
        }
    }

}
