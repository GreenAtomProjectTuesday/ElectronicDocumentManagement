package electonic.document.management.service;

import electonic.document.management.model.document.Document;
import electonic.document.management.model.document.DocumentState;
import electonic.document.management.model.User;
import electonic.document.management.repository.DocumentStateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DocumentStateService {
    private final DocumentStateRepository documentStateRepository;

    public DocumentStateService(DocumentStateRepository documentStateRepository) {
        this.documentStateRepository = documentStateRepository;
    }


    public DocumentState createState(Document document, User user, String commitMessage) {
        DocumentState documentState = new DocumentState();
        documentState.setDocument(document);
        documentState.setCommitDate(LocalDateTime.now());
        documentState.setCreator(user);
        documentState.setCommitMessage(commitMessage);

        return documentState;
    }

    public void saveState(DocumentState documentState) {
        documentStateRepository.save(documentState);
    }
}
