package electonic.document.management.service;

import electonic.document.management.model.Document;
import electonic.document.management.repository.DocumentNamesOnly;
import electonic.document.management.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public boolean addDocument(Document document) {
        Document documentFromDb = documentRepository.getDocumentByFileName(document.getFileName());

        if (documentFromDb != null) {
            return false;
        }

        documentRepository.save(document);
        return true;
    }

    public List<DocumentNamesOnly> getAllDocumentNames() {
        return documentRepository.findAllBy();
    }
}
