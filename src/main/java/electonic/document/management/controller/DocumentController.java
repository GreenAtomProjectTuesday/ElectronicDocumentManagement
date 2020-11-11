package electonic.document.management.controller;

import electonic.document.management.model.Document;
import electonic.document.management.service.DocumentService;
import electonic.document.management.utils.DocumentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class DocumentController {
    private final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);
    private final DocumentService documentService;
    private final DocumentUtils documentUtils;

    public DocumentController(DocumentService documentService, DocumentUtils documentUtils) {
        this.documentService = documentService;
        this.documentUtils = documentUtils;
    }

    //TODO handle exception?
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
}
