package net.dmfe.organizer;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryTasksRepository implements TasksRepository {

    private final List<Task> tasks = new LinkedList<>();

    @Override
    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return tasks.stream()
                .filter(task -> task.id().equals(id))
                .findFirst();
    }

    @Override
    public void save(Task task) {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

}
