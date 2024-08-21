package net.dmfe.organizer;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository(JdbcOperationsTasksRepository.JDBC_OPERATIONS)
public class JdbcOperationsTasksRepository implements TasksRepository {

    public static final String JDBC_OPERATIONS = "jdbc_operations_tasks";

    private final JdbcOperations jdbcOperations;

    public JdbcOperationsTasksRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Task> findAll() {
        return jdbcOperations.query("""
                select * from org.tasks
                """, (rs, rowNum) -> new Task(
                    rs.getObject("id", UUID.class),
                    rs.getString("details"),
                    rs.getBoolean("completed")
                )
        );
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return jdbcOperations.query("""
                select * from org.tasks where id = ?
                """, (rs, rowNum) -> new Task(
                    rs.getObject("id", UUID.class),
                    rs.getString("details"),
                    rs.getBoolean("completed")
                ), id
        ).stream().findFirst();
    }

    @Override
    public void save(Task task) {
        jdbcOperations.update("""
                insert into org.tasks (id, details, completed) values (?, ?, ?)
                """, task.id(), task.details(), task.completed());
    }
}
