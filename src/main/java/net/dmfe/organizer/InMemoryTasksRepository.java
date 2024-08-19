package net.dmfe.organizer;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class InMemoryTasksRepository implements TasksRepository {

    private final List<Task> tasks = new LinkedList<>() {{
        add(new Task("Task one"));
        add(new Task("Task two"));
    }};

    @Override
    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public void save(Task task) {
        tasks.add(task);
    }

}
