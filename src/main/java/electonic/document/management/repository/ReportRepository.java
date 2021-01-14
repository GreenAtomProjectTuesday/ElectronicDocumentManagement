package electonic.document.management.repository;

import electonic.document.management.model.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Task, Long> {
    @EntityGraph(value = "task-entity-graph-for-report", type = EntityGraph.EntityGraphType.LOAD)
    Task getTaskByName(String taskName);


}