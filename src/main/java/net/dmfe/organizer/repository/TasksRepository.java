package net.dmfe.organizer.repository;

import net.dmfe.organizer.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TasksRepository {

    List<Task> findAll();

    Optional<Task> findById(UUID id);

    Optional<Task> findByIdAndByAppUserID(UUID id, UUID userId);

    List<Task> findByAppUserId(UUID id);

    void save(Task task);

}
