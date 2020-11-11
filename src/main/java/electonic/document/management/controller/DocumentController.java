package electonic.document.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Document;
import electonic.document.management.service.DocumentService;
import electonic.document.management.utils.DocumentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class DocumentController {
    private final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);
    private final DocumentService documentService;
    private final DocumentUtils documentUtils;
    private final ObjectMapper objectMapper;

    public DocumentController(DocumentService documentService, DocumentUtils documentUtils, ObjectMapper objectMapper) {
        this.documentService = documentService;
        this.documentUtils = documentUtils;
        this.objectMapper = objectMapper;
    }

    //TODO handle exception?
    //TODO add attributes
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        LOGGER.info("Request for file import ");
        if (file.isEmpty()) {
            return ResponseEntity.ok("file is empty");
        }

        Document document = documentUtils.fileToDocument(file);

        if (!documentService.addDocument(document)) {
            return ResponseEntity.ok("Document already exists!");
        }

        return ResponseEntity.ok("Document successfully uploaded!");
    }

    @GetMapping("/getAllDocumentNames")
    public ResponseEntity<?> getAllDocumentNames() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(documentService.getAllDocumentNames()));
    }
}
