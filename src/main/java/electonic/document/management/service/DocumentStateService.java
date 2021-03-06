package electonic.document.management.service;

import electonic.document.management.model.document.Document;
import electonic.document.management.model.document.DocumentState;
import electonic.document.management.model.document.DocumentStateType;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.User;
import electonic.document.management.repository.DocumentStateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DocumentStateService {
    private final DocumentStateRepository documentStateRepository;

    public DocumentStateService(DocumentStateRepository documentStateRepository) {
        this.documentStateRepository = documentStateRepository;
    }


    public DocumentState createState(Document document, Employee employee, String commitMessage,
                                     DocumentStateType documentStateType) {
        DocumentState documentState = new DocumentState();
        documentState.setDocument(document);
        documentState.setCommitDate(LocalDateTime.now());
        documentState.setCreator(employee);
        documentState.setCommitMessage(commitMessage);
        documentState.setDocumentStateType(documentStateType);

        return documentState;
    }

    public void saveState(DocumentState documentState) {
        documentStateRepository.save(documentState);
    }
}
