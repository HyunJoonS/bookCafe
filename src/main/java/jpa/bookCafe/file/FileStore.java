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

    //파일 저장
    public String storeFile(MultipartFile file) throws IOException {
        String storeFilename = createStoreFilename(file.getOriginalFilename());
        file.transferTo(new File(fileDir+storeFilename)); //경로+이름으로 저장
        return storeFilename;
                
    }

    //파일 저장을 위한 랜덤이름.확장자를 리턴하는 메서드
    private String createStoreFilename(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        String storeFilename = uuid + "." + ext;
        return storeFilename;
    }

    //확장자를 뽑아내는 메서드
    private String extractExt(String originalFilename) {
        int pos = originalFilename.indexOf('.');
        String ext = originalFilename.substring(pos + 1);
        return ext;
    }
}
