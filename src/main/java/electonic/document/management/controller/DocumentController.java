package electonic.document.management.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Document;
import electonic.document.management.model.Task;
import electonic.document.management.model.User;
import electonic.document.management.model.Views;
import electonic.document.management.service.DocumentService;
import electonic.document.management.utils.DocumentUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("documents")
public class DocumentController {
    private final DocumentService documentService;
    private final ObjectMapper objectMapper;

    public DocumentController(DocumentService documentService, DocumentUtils documentUtils, ObjectMapper objectMapper) {
        this.documentService = documentService;
        this.objectMapper = objectMapper;
    }

    //TODO handle exception?
    //TODO add attributes
    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("task_id") Task task,
                                             @AuthenticationPrincipal User user) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.ok("file is empty");
        }

        if (!documentService.addDocument(file, user, task)) {
            return ResponseEntity.ok("Document already exists!");
        }

        return ResponseEntity.ok("Document was successfully uploaded!");
    }

    //TODO add document body to ResponseBody
    @GetMapping("{id}")
    public ResponseEntity<String> downloadDocument(@PathVariable("id") Long id) {
        Document document = documentService.getDocumentById(id);
        return ResponseEntity.ok("Document returned");
    }

    @PatchMapping("{document_id}")
    public ResponseEntity<String> editDocument(@PathVariable("document_id") Document document,
                                               @RequestParam("new_file") MultipartFile file) throws IOException {
        documentService.editDocument(document, file);
        return ResponseEntity.ok("Document was successfully edited");
    }

    @GetMapping
    public ResponseEntity<String> getAllDocumentNames() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.IdName.class)
                .writeValueAsString(documentService.getAllDocumentNames()));
    }

    @DeleteMapping("{document_id}")
    public ResponseEntity<String> deleteDocument(@PathVariable("document_id") Document document) {
        documentService.deleteDocument(document);
        return ResponseEntity.ok("Document was successfully deleted");
    }
}
