package net.dmfe.organizer;

import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    private final TasksRepository tasksRepository;
    private final MessageSource messageSource;

    public TasksController(TasksRepository tasksRepository, MessageSource messageSource) {
        this.tasksRepository = tasksRepository;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ResponseEntity<List<Task>> handleGetAllTasks() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(tasksRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> handleCreateTask(
            @RequestBody NewTaskPayload taskPayload,
            UriComponentsBuilder uriComponentsBuilder,
            Locale locale
    ) {
        if (taskPayload.details() == null || taskPayload.details().isBlank()) {
            var errorMessage = messageSource.getMessage("errors.task.details.is_not_defined",
                    new Object[0], locale);
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorResponse(List.of(errorMessage)));
        }

        var task = new Task(taskPayload.details());
        tasksRepository.save(task);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("/api/tasks/{taskId}")
                        .build(Map.of("taskId", task.id()))
                )
                .contentType(MediaType.APPLICATION_JSON)
                .body(task);
    }

}
