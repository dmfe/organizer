package net.dmfe.organizer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class TasksControllerTest {
    private static final String BASE_LOCATION = "http://lcalhost:8080";

    @Mock
    private TasksRepository tasksRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private TasksController tasksController;

    @Test
    @DisplayName("GET /api/tasks returns HTTP-response with status 200 and list of tasks")
    void handleGetAllTasks_ReturnsValidResponseEntity() {
        // given
        var tasks = List.of(
                new Task(UUID.randomUUID(), "Task one", false),
                new Task(UUID.randomUUID(), "Task two", true)
        );
        doReturn(tasks).when(tasksRepository).findAll();

        // when
        var responseEntity = tasksController.handleGetAllTasks();

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(tasks, responseEntity.getBody());
    }

    @Test
    @DisplayName("POST /api/tasks with valid payload returns HTTP-response with 201 code, location and created task")
    void handleCreateTask_PayloadIsValid_ReturnsValidResponseEntity() {
        // given
        var details = "Task three";

        // when
        var responseEntity = tasksController.handleCreateTask(
                new NewTaskPayload(details),
                UriComponentsBuilder.fromUriString(BASE_LOCATION),
                Locale.ENGLISH
        );

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        if (responseEntity.getBody() instanceof Task task) {
            assertNotNull(task.id());
            assertEquals(details, task.details());
            assertFalse(task.completed());
            assertEquals(
                    URI.create(String.format("%s/api/tasks/%s", BASE_LOCATION, task.id())),
                    responseEntity.getHeaders().getLocation()
            );
            verify(tasksRepository).save(task);
        } else {
            assertInstanceOf(Task.class, responseEntity.getBody());
        }
        verifyNoMoreInteractions(tasksRepository);
    }

    @Test
    @DisplayName("POST /api/tasks with invalid payload returns HTTP-response with 400 code and list of errors")
    void handleCreateTask_PayloadIsInvalid_ReturnsValidResponseEntity() {
        // given
        var details = "  ";
        var errorMessage = "Task details not defined";
        var locale = Locale.ENGLISH;

        doReturn(errorMessage).when(messageSource).getMessage(
                "errors.task.details.is_not_defined",
                new Object[0],
                locale);

        // when
        var responseEntity = tasksController.handleCreateTask(
                new NewTaskPayload(details),
                UriComponentsBuilder.fromUriString(BASE_LOCATION),
                locale
        );

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(new ErrorResponse(List.of(errorMessage)), responseEntity.getBody());
        verifyNoMoreInteractions(tasksRepository);
    }

}
