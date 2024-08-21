package net.dmfe.organizer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/sql/tasks_rest_controller/test_data.sql")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class TasksControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void handleGetAllTasks_ReturnsValidResponse() throws Exception {
        // given
        var requestBuilder = get("/api/tasks");

        // when
        mockMvc.perform(requestBuilder)

                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                        "id": "a8851f7b-6341-47b5-8584-617b55a8cf5e",
                                        "details": "Task one",
                                        "completed": false
                                    },
                                    {
                                        "id": "d7a24240-ed79-4332-994a-8f2aeb3ba277",
                                        "details": "Task two",
                                        "completed": true
                                    }
                                ]
                                """)
                );
    }

    @Test
    void handleCreateTask_PayloadIsValid_ReturnsValidResponse() throws Exception {
        // given
        var requestBuilder = post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "details": "Task three"
                        }
                        """);

        // when
        mockMvc.perform(requestBuilder)

                // then
                .andExpectAll(
                        status().isCreated(),
                        header().exists(HttpHeaders.LOCATION),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "details": "Task three",
                                    "completed": false
                                }
                                """
                        ),
                        jsonPath("$.id").exists()
                );
    }

    @Test
    void handleCreateTask_PayloadIsInvalid_ReturnsValidResponse() throws Exception {
        // given
        var requestBuilder = post("/api/tasks")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "details": "   "
                        }
                        """);

        // when
        mockMvc.perform(requestBuilder)

                // then
                .andExpectAll(
                        status().isBadRequest(),
                        header().doesNotExist(HttpHeaders.LOCATION),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "errors": ["Task details not defined"]
                                }
                                """, true
                        )
                );
    }

}
