package net.dmfe.organizer;

import java.util.List;

public interface TasksRepository {

    List<Task> findAll();

    void save(Task task);

}
