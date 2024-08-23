package net.dmfe.organizer.repository;

import net.dmfe.organizer.entity.Task;
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
    public Optional<Task> findByIdAndByAppUserID(UUID id, UUID userId) {
        return tasks.stream()
                .filter(task -> task.id().equals(id) && task.userId().equals(userId))
                .findFirst();
    }

    @Override
    public List<Task> findByAppUserId(UUID id) {
        return tasks.stream()
                .filter(task -> task.userId().equals(id))
                .toList();
    }

    @Override
    public void save(Task task) {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

}
