package net.dmfe.organizer;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    private final TasksRepository tasksRepository;

    public TasksController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @GetMapping
    public ResponseEntity<List<Task>> handleGetAllTasks() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(tasksRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Task> handleCreateTask(
            @RequestBody NewTaskPayload taskPayload,
            UriComponentsBuilder uriComponentsBuilder
    ) {
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
