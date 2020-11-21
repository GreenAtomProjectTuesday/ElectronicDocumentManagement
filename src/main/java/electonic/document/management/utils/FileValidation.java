package electonic.document.management.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//TODO how to add msword validation
// is it even needed?
public class FileValidation {
    private static final List<String> contentTypes = Arrays.asList("application/msword", "application/pdf");
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static boolean validateFile(MultipartFile file) throws IOException {
        String fileContentType = file.getContentType();
        if (Objects.equals(fileContentType, contentTypes.get(1))) {
            return isValidPDF(file.getInputStream());
        }
        return false;
    }

    private static boolean isValidPDF(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[4];
        int n = inputStream.read(buffer, 0, 4);
        String bufferString = bytesToHex(buffer);
        return Objects.equals(bufferString, "25504446");
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
