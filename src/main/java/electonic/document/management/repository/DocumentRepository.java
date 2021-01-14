package electonic.document.management.repository;

import electonic.document.management.model.document.Document;
import electonic.document.management.repository.projections.DocumentDetailsChooseI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document getDocumentByName(String filename);
    List<DocumentDetailsChooseI> getAllDocumentsByTaskName(String taskName);

    @Query("select d.owner.id from Document d where d.name = ?1")
    Long getUserIdByName(String filename);
}
