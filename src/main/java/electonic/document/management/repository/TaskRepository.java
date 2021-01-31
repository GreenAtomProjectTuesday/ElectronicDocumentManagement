package electonic.document.management.repository;

import electonic.document.management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task getTaskByName(String taskName);
}
