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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integrationTest")
class GetUsersApiTest {

    private final UsersApiRequestBuilder apiRequestBuilder;


    public GetUsersApiTest(@Autowired MockMvc mockMvc) {
        this.apiRequestBuilder = new UsersApiRequestBuilder(mockMvc);
    }

    @Nested
    @DisplayName("When accessing as admin")
    @Sql({
            "/db/clean-users-database.sql",
            "/db/init-users.sql"
    })
    @WithMockUser(value = "test@test.com", roles = "ADMIN")
    class WhenAccessingAsAdmin {

        @Test
        @DisplayName("Should return HTTP status code ok on GET")
        void shouldReturnHttpStatusCodeOkGET() throws Exception {
            apiRequestBuilder.findAll()
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return the found items as JSON on GET")
        void shouldReturnFoundTodoItemsJSONOnGET() throws Exception {
            apiRequestBuilder.findAll()
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should return an items list on GET")
        void shouldReturnEmptyListOnGet() throws Exception {
            apiRequestBuilder.findAll()
                    .andExpect(jsonPath("$", hasSize(2)));
        }
    }

    @Nested
    @DisplayName("When accessing as non admin")
    @Sql({
            "/db/clean-users-database.sql",
            "/db/init-users.sql"
    })
    @WithMockUser(value = "test@test.com", roles = "USER")
    class WhenAccessingAsNonAdmin {

        @Test
        @DisplayName("Should return HTTP status code ok")
        void shouldReturnHttpStatusCodeOk() throws Exception {
            apiRequestBuilder.findAll()
                    .andExpect(status().isForbidden());
        }
    }
}
