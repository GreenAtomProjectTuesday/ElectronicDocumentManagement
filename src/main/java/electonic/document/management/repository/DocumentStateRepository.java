package electonic.document.management.repository;

import electonic.document.management.model.document.DocumentState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentStateRepository extends JpaRepository<DocumentState, Long> {
}
