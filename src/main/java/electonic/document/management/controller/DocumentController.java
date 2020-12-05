package electonic.document.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.*;
import electonic.document.management.model.document.Document;
import electonic.document.management.model.document.DocumentState;
import electonic.document.management.service.DocumentStateService;
import electonic.document.management.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("documents")
public class DocumentController {
    private final DocumentService documentService;
    private final DocumentStateService documentStateService;
    private final ObjectMapper objectMapper;

    public DocumentController(DocumentService documentService, DocumentStateService documentStateService, ObjectMapper objectMapper) {
        this.documentService = documentService;
        this.documentStateService = documentStateService;
        this.objectMapper = objectMapper;
    }

    //TODO handle exception?
    //TODO add attributes
    //TODO create exception
    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("task_id") Task task,
                                             @AuthenticationPrincipal User user,
                                             @RequestParam(name = "commitMessage", defaultValue = "create file") String commitMessage) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.ok("file is empty");
        }

        Document document = documentService.createDocument(file, user, task);
        DocumentState documentState = documentStateService.createState(document, user, commitMessage);
        document.getDocumentStates().add(documentState);

        documentService.saveDocument(document);

        return ResponseEntity.ok("Document was successfully uploaded!");
    }

    //TODO add document body to ResponseBody
    @GetMapping("{id}")
    public ResponseEntity<?> downloadDocument(@PathVariable("id") Long id) {
        Document document = documentService.getDocumentById(id);
        return ResponseEntity.ok("Document returned");
    }

    @PostMapping("{document_id}")
    // TODO: 05.12.2020 Keep old documents?
    public ResponseEntity<?> editDocument(@PathVariable("document_id") Document document,
                                               @RequestParam("new_file") MultipartFile file,
                                               @AuthenticationPrincipal User user,
                                               @RequestParam(name = "commitMessage", defaultValue = "edit file") String commitMessage) throws IOException {
        DocumentState documentState = documentStateService.createState(document, user, commitMessage);
        documentService.editDocument(document, file, documentState);
        return ResponseEntity.ok("Document was successfully edited. New state " + documentState.getCommitMessage());
    }

    @GetMapping
    public ResponseEntity<?> getAllDocumentNames() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.IdName.class)
                .writeValueAsString(documentService.getAllDocumentNames()));
    }

    @DeleteMapping("{document_id}")
    // TODO: 03.12.2020 store in history?
    public ResponseEntity<?> deleteDocument(@PathVariable("document_id") Document document) {
        documentService.deleteDocument(document);
        return ResponseEntity.ok("Document was successfully deleted");
    }
}
