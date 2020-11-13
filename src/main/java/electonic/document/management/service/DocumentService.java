package electonic.document.management.service;

import electonic.document.management.model.Document;
import electonic.document.management.model.User;
import electonic.document.management.projections.DocumentNamesOnly;
import electonic.document.management.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public boolean addDocument(Document document, User user) {
        Document documentFromDb = documentRepository.getDocumentByFileName(document.getFileName());

        if (documentFromDb != null) {
            return false;
        }
        document.setCreationDate(LocalDateTime.now());
        document.setOwner(user);

        documentRepository.save(document);
        return true;
    }

    //TODO replace with view?
    public List<DocumentNamesOnly> getAllDocumentNames() {
        return documentRepository.findAllBy(DocumentNamesOnly.class);
    }
}
