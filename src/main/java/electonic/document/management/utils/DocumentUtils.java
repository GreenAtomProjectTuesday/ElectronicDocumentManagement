package electonic.document.management.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Document;
import electonic.document.management.model.DocumentAttribute;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class DocumentUtils {

    private final ObjectMapper objectMapper;

    public DocumentUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // TODO improve this
    public Document fileToDocument(MultipartFile file) throws IOException {
        Document document = new Document();

        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setSize(file.getSize());


        document.setContent(file.getBytes());

        return document;
    }

}
