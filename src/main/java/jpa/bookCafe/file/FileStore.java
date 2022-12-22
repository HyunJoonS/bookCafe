package jpa.bookCafe.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${file.dir}")
    String fileDir;

    public String storeFile(MultipartFile file) throws IOException {
        String storeFilename = createStoreFilename(file.getOriginalFilename());
        file.transferTo(new File(fileDir+storeFilename));
        return storeFilename;
                
    }

    private String createStoreFilename(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        String storeFilename = uuid + "." + ext;
        return storeFilename;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.indexOf('.');
        String ext = originalFilename.substring(pos + 1);
        return ext;
    }
}
