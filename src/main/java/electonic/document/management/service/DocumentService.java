package electonic.document.management.service;

import electonic.document.management.model.RequestParametersException;
import electonic.document.management.model.document.Document;
import electonic.document.management.model.Task;
import electonic.document.management.model.document.DocumentStateType;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.User;
import electonic.document.management.model.document.DocumentState;
import electonic.document.management.repository.DocumentRepository;
import electonic.document.management.utils.DocumentUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final DocumentStateService documentStateService;

    public DocumentService(DocumentRepository documentRepository, DocumentUtils documentUtils, DocumentStateService documentStateService) {
        this.documentRepository = documentRepository;
        this.documentUtils = documentUtils;
        this.documentStateService = documentStateService;
    }

    @Transactional
    public void createDocument(MultipartFile file, Employee employee, Task task, String commitMessage)
            throws IOException, RequestParametersException {
        Document document = new Document();
        documentUtils.fileToDocument(document, file);

        Document documentFromDb = documentRepository.getDocumentByName(document.getName());
        if (documentFromDb != null) {
            throw new RequestParametersException("Document with such name already exists!");
        }

        document.setCreationDate(LocalDateTime.now());
        document.setTask(task);
        document.setDocumentStates(new LinkedList<>());

        DocumentState documentState = documentStateService.createState(document, employee, commitMessage, DocumentStateType.CREATE);
        document.getDocumentStates().add(documentState);

        documentRepository.save(document);
    }

    public List<Document> getAllDocumentNames() {
        return documentRepository.findAll();
    }

    public Document getDocumentById(Long id) throws RequestParametersException {
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isEmpty()) {
            throw new RequestParametersException("There is no Document with such id!");
        }
        return optionalDocument.get();
    }

    public void editDocument(Document document, MultipartFile file, DocumentState documentState) throws IOException {
        documentUtils.fileToDocument(document, file);
        document.getDocumentStates().add(documentState);
        documentRepository.save(document);
    }

    public void deleteDocument(Document document) {
        documentRepository.delete(document);
    }

    public List<Document> findDocumentsByExample(String subStringInName, String fileUuid, String fileType,
                                                 Task task) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withMatcher("name", match -> match.contains())
                .withMatcher("fileUuid", match -> match.contains())
                .withMatcher("fileType", match -> match.contains());

        Document document = new Document();
        document.setName(subStringInName);
        document.setFileUuid(fileUuid);
        document.setFileType(fileType);
        document.setTask(task);

        Example<Document> documentExample = Example.of(document, matcher);
        return documentRepository.findAll(documentExample);
    }
}
