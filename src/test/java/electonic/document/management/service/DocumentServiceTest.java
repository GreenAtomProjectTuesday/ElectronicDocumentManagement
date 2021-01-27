package electonic.document.management.service;

import electonic.document.management.controller.DocumentController;
import electonic.document.management.model.document.Document;
import electonic.document.management.model.user.User;
import electonic.document.management.repository.DocumentRepository;
import electonic.document.management.repository.user.DepartmentEmployeeRepository;
import electonic.document.management.service.user.DepartmentEmployeeService;
import electonic.document.management.utils.DocumentUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.config.Task;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DocumentServiceTest {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentController documentController;

    @MockBean
    private DocumentRepository documentRepository;

    @MockBean
    private DocumentStateService documentStateService;


    @Test
    void previewDocument() throws IOException {
        Document document = new Document();
        DocumentUtils documentUtils = new DocumentUtils();

        MockMultipartFile jsonFile = new MockMultipartFile("test.json", "",
                "application/json", "{\"key2\": \"value2\"}".getBytes());

        document.setName(jsonFile.getOriginalFilename());
        document.setFileType(jsonFile.getContentType());
        document.setSize(jsonFile.getSize());
        document.setContent(jsonFile.getBytes());

        byte[] previewDocument = documentService.previewDocument(document);

        Assert.assertNotNull(previewDocument);

    }
}