package net.jaram.indoornavigation.service;

import net.jaram.indoornavigation.dto.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    UploadFileResponse upload(MultipartFile file, String fileName) throws IOException;

    byte[] download(String fileKey) throws IOException;
}
