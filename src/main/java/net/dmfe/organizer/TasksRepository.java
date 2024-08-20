package net.dmfe.organizer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TasksRepository {

    List<Task> findAll();

    Optional<Task> findById(UUID id);

    void save(Task task);

}
