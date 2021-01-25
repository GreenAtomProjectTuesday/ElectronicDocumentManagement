package electonic.document.management.service;

import electonic.document.management.model.document.Document;
import electonic.document.management.model.Task;
import electonic.document.management.model.user.User;
import electonic.document.management.model.document.DocumentState;
import electonic.document.management.repository.DocumentRepository;
import electonic.document.management.utils.DocumentUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentUtils documentUtils;

    public DocumentService(DocumentRepository documentRepository, DocumentUtils documentUtils) {
        this.documentRepository = documentRepository;
        this.documentUtils = documentUtils;
    }

    public Document createDocument(MultipartFile file, User user, Task task) throws IOException {
        Document document = new Document();
        documentUtils.fileToDocument(document, file);
        Document documentFromDb = documentRepository.getDocumentByName(document.getName());

        //TODO exception documentFromDb!=null
        document.setCreationDate(LocalDateTime.now());
        document.setOwner(user);
        document.setTask(task);
        document.setDocumentStates(new LinkedList<>());

        return document;
    }

    public List<Document> getAllDocumentNames() {
        return documentRepository.findAll();
    }

    // TODO add exception for optional handling?
    public Document getDocumentById(Long id) {
        Optional<Document> document = documentRepository.findById(id);
        return document.get();
    }

    public void editDocument(Document document, MultipartFile file, DocumentState documentState) throws IOException {
        documentUtils.fileToDocument(document, file);
        document.getDocumentStates().add(documentState);
        documentRepository.save(document);
    }

    public void deleteDocument(Document document) {
        documentRepository.delete(document);
    }


    public void saveDocument(Document document) {
        documentRepository.save(document);
    }

    public void previewDocument() {

    }
}
