package electonic.document.management.utils;

import electonic.document.management.model.document.Document;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class DocumentUtils {

    public void fileToDocument(Document document, MultipartFile file) throws IOException {
        document.setName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setSize(file.getSize());
        document.setContent(file.getBytes());
    }

}
