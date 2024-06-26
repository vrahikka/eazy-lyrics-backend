package com.eazyLyrics.backend.users;

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
        "/db/clean-users-database.sql"
})
@WithMockUser(value = "test@test.com", roles = "ADMIN")
class CreateUserApiTest {

    private final UsersApiRequestBuilder apiRequestBuilder;
    private final Table usersTable;

    @Autowired
    public CreateUserApiTest(MockMvc mockMvc, DataSource dataSource) {
        this.apiRequestBuilder = new UsersApiRequestBuilder(mockMvc);
        this.usersTable = new Table(dataSource, "users");
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
        @DisplayName("Should return validation error")
        void shouldReturnOneValidationError() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(jsonPath("$.validationErrors", hasSize(2)));
        }

        @Test
        @DisplayName("Should return validation error about empty email")
        void shouldReturnValidationErrorAboutEmptyEmailUrl() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(jsonPath(
                            "$.validationErrors[?(@.field == 'email')].errorCode",
                            contains("NotNull")
                    ));
        }

        @Test
        @DisplayName("Should return validation error about empty password")
        void shouldReturnValidationErrorAboutEmptyPasswordUrl() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(jsonPath(
                            "$.validationErrors[?(@.field == 'password')].errorCode",
                            contains("NotNull")
                    ));
        }

        @Test
        @DisplayName("Shouldn't insert a new song into the database")
        void shouldNotInsertNewTodoItemIntoDatabase() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY);
            assertThat(usersTable).hasNumberOfRows(0);
        }
    }

    @Nested
    @DisplayName("When input data is valid")
    class WhenInputDataIsValid {

        private final String REQUEST_BODY = """
                {
                    "email": "test@test.com",
                    "password": "123"
                }
                """;

        @Test
        @DisplayName("Should return the HTTP status code created")
        void shouldReturnHttpStatusCodeCreated() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("Should insert one todo item into the favoriteSongs table")
        void shouldInsertOneTodoItemIntoTodoItemTable() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY);
            assertThat(usersTable).hasNumberOfRows(1);
        }

        @Test
        @DisplayName("Should insert the correct id into the favoriteSongs table")
        void shouldInsertCorrectIdIntoTodoItemTable() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY);
            assertThat(usersTable)
                    .row(0)
                    .value("id")
                    .isEqualTo(1L);
        }


        @Test
        @DisplayName("Should insert correct item into the favoriteSongs table")
        void shouldInsertCorrectTodoItemIntoTodoItemTable() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY);

            var softAssertions = new SoftAssertions();

            softAssertions.assertThat(usersTable)
                    .row(0)
                    .value("id")
                    .as("id")
                    .isEqualTo(1L);
            softAssertions.assertThat(usersTable)
                    .row(0)
                    .value("email")
                    .as("email")
                    .isEqualTo("test@test.com");

            softAssertions.assertAll();
        }
    }

    @Nested
    @DisplayName("When user already exists")
    @Sql({
            "/db/clean-users-database.sql",
            "/db/init-users.sql"
    })
    class WhenUserAlreadyExists {

        private final String REQUEST_BODY = """
                {
                    "email": "test@test.com",
                    "password": "123"
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

    @Nested
    @DisplayName("When user already exists")
    @Sql({
            "/db/clean-users-database.sql",
            "/db/init-users.sql"
    })
    @WithMockUser(value = "test@test.com", roles = "USER")
    class WhenNonAdmin {

        private final String REQUEST_BODY = """
                {
                    "email": "test@test.com",
                    "password": "123"
                }
                """;

        @Test
        @DisplayName("Should return the HTTP status code bad request")
        void shouldReturnHttpStatusCodeBadRequest() throws Exception {
            apiRequestBuilder.create(REQUEST_BODY)
                    .andExpect(status().isForbidden());
        }
    }

}
