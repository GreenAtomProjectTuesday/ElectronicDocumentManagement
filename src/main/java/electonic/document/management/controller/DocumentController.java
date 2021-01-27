package electonic.document.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.*;
import electonic.document.management.model.document.Document;
import electonic.document.management.model.document.DocumentState;
import electonic.document.management.model.document.DocumentStateType;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.User;
import electonic.document.management.service.DocumentStateService;
import electonic.document.management.service.DocumentService;
import electonic.document.management.service.user.EmployeeService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("documents")
public class DocumentController {
    private final DocumentService documentService;
    private final DocumentStateService documentStateService;
    private final ObjectMapper objectMapper;
    private final EmployeeService employeeService;

    public DocumentController(DocumentService documentService, DocumentStateService documentStateService, ObjectMapper objectMapper, EmployeeService employeeService) {
        this.documentService = documentService;
        this.documentStateService = documentStateService;
        this.objectMapper = objectMapper;
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("task_id") Task task,
                                        @AuthenticationPrincipal User user,
                                        @RequestParam(name = "commitMessage", defaultValue = "create file") String commitMessage) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.ok("file is empty");
        }
        try {
            Employee employee = employeeService.getEmployeeByUserId(user.getId());
            documentService.createDocument(file, employee, task, commitMessage);
        } catch (RequestParametersException e) {
            return ResponseEntity.ok(e.getMessage());
        }

        return ResponseEntity.ok("Document was successfully uploaded!");
    }

    //TODO test it
    @GetMapping("{id}")
    public ResponseEntity<?> downloadDocument(@PathVariable("id") Long id) {
        try {
            Document document = documentService.getDocumentById(id);
            InputStream input = new ByteArrayInputStream(document.getContent());
            InputStreamResource resource = new InputStreamResource(input);

            return ResponseEntity
                    .ok()
                    .contentLength(document.getSize())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (RequestParametersException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("{document_id}")
    // TODO: 05.12.2020 Keep old documents?
    public ResponseEntity<?> editDocument(
            @PathVariable("document_id") Document document,
            @RequestParam("new_file") MultipartFile file,
            @AuthenticationPrincipal User user,
            @RequestParam(name = "commitMessage", defaultValue = "edit file") String commitMessage) throws IOException {
        try {
            Employee employee = employeeService.getEmployeeByUserId(user.getId());
            DocumentState documentState = documentStateService.createState(document, employee, commitMessage, DocumentStateType.UPDATE);
            documentService.editDocument(document, file, documentState);
            return ResponseEntity.ok("Document was successfully edited. New state " + documentState.getCommitMessage());
        } catch (RequestParametersException e) {
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllDocumentNames() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.DocumentParameters.class)
                .writeValueAsString(documentService.getAllDocumentNames()));
    }

    @DeleteMapping("{document_id}")
    // TODO: 03.12.2020 store in history
    public ResponseEntity<?> deleteDocument(@PathVariable("document_id") Document document) {
        documentService.deleteDocument(document);
        return ResponseEntity.ok("Document was successfully deleted");
    }

    //TODO search by size, by date in range and by attribute in list
    @GetMapping("with_params")
    public ResponseEntity<?> findDocuments(
            @RequestParam(value = "name_contains", required = false) String subStringInName,
            @RequestParam(value = "file_uuid", required = false) String fileUuid,
            @RequestParam(value = "file_type", required = false) String fileType,
            @RequestParam(value = "task_id", required = false) Task task
    ) throws JsonProcessingException {
        if (subStringInName == null && fileUuid == null && fileType == null
                && task == null)
            return ResponseEntity.ok("No parameters were specified");
        List<Document> departmentsWithParams = documentService
                .findDocumentsByExample(subStringInName, fileUuid, fileType, task);
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.DocumentParameters.class)
                .writeValueAsString(departmentsWithParams));
    }
}
