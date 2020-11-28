package electonic.document.management.repository;

import electonic.document.management.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document getDocumentByFileName(String filename);
}
