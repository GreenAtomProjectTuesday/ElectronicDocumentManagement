package electonic.document.management.repository;

import electonic.document.management.model.Message;
import electonic.document.management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> getAllByTask(Task task);
}
