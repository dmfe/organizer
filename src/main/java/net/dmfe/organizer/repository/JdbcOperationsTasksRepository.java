package net.dmfe.organizer.repository;

import net.dmfe.organizer.entity.Task;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository(JdbcOperationsTasksRepository.JDBC_OPERATIONS)
public class JdbcOperationsTasksRepository implements TasksRepository {

    public static final String JDBC_OPERATIONS = "jdbc_operations_tasks";

    private static final RowMapper<Task> TASK_ROW_MAPPER = (rs, rowNum) -> new Task(
            rs.getObject("id", UUID.class),
            rs.getString("details"),
            rs.getBoolean("completed"),
            rs.getObject("app_user_id", UUID.class)
    );

    private final JdbcOperations jdbcOperations;

    public JdbcOperationsTasksRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Task> findAll() {
        return jdbcOperations.query("""
                select * from org.tasks
                """, TASK_ROW_MAPPER
        );
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return jdbcOperations.query("""
                select * from org.tasks where id = ?
                """, TASK_ROW_MAPPER, id
        ).stream().findFirst();
    }

    @Override
    public Optional<Task> findByIdAndByAppUserID(UUID id, UUID userId) {
        return jdbcOperations.query("""
                select * from org.tasks where id = ? and app_user_id = ?
                """, TASK_ROW_MAPPER, id, userId).stream().findFirst();
    }

    @Override
    public List<Task> findByAppUserId(UUID userId) {
        return jdbcOperations.query("""
                select * from org.tasks where app_user_id = ?
                """, TASK_ROW_MAPPER, userId);
    }

    @Override
    public void save(Task task) {
        jdbcOperations.update("""
                insert into org.tasks (id, details, completed, app_user_id) values (?, ?, ?, ?)
                """, task.id(), task.details(), task.completed(), task.userId());
    }
}
