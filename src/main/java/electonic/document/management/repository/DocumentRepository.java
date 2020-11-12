package electonic.document.management.repository;

import electonic.document.management.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document getDocumentByFileName(String filename);

    <T> List<T> findAllBy(Class<T> type);
}
