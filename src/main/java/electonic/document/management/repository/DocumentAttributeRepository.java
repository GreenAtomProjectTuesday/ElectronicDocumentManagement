package electonic.document.management.repository;

import electonic.document.management.model.document.DocumentAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentAttributeRepository extends JpaRepository<DocumentAttribute, Long> {
    DocumentAttribute getDocumentAttributeByName(String name);
}
