package electonic.document.management.utils;

import electonic.document.management.model.document.Document;
import electonic.document.management.service.TaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Component
public class DocumentUtils {

    // TODO: 27.01.2021 why it is not working with tests
    // TODO: 27.01.2021 remove C:/java/ElectronicDocumentManagement
//    @Value("${upload.path}")
//    private static String uploadPath;
        private static String uploadPath = "C:/java/ElectronicDocumentManagement/uploads";

    public void fileToDocument(Document document, MultipartFile file) throws IOException {
        document.setName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setSize(file.getSize());

        UUID uuid = UUID.nameUUIDFromBytes(file.getBytes());
        document.setFileUuid(uuid);
    }

    public void saveFile(Document document, MultipartFile file) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String fileDirectory = uploadPath + "/" + document.getTask().getTaskUuid().toString();
            File uploadDir = new File(fileDirectory);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String fileName = document.getFileUuid().toString();
            String filePath = fileDirectory + "/" + fileName;
            file.transferTo(new File(filePath));
            document.setFilePath(filePath);
        }
    }

}
