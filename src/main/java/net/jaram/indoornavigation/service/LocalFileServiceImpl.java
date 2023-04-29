package net.jaram.indoornavigation.service;

import lombok.RequiredArgsConstructor;
import net.jaram.indoornavigation.dto.UploadFileResponse;
import net.jaram.indoornavigation.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class LocalFileServiceImpl implements FileService {
    @Value("${file.upload-dir}")
    private Path fileStorageLocation;

    @Autowired
    public void FileLocalServiceImpl() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Failed to create directory.", ex);
        }
    }

    @Override
    public UploadFileResponse upload(MultipartFile file, String fileName) {
        UploadFileResponse uploadFileResponse;
        fileName = storeFile(file, fileName);
        try {
            if (fileName.equals("")) {
                throw new Exception("The file name does not exist.");
            } else {
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/downloadFile/")
                        .path(fileName)
                        .toUriString();
                uploadFileResponse = new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
            }
        } catch (Exception ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

        return uploadFileResponse;
    }

    public String storeFile(MultipartFile file, String fileName) {
        fileName = StringUtils.cleanPath(fileName);
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

        return fileName;
    }

    @Override
    public byte[] download(String fileKey) throws IOException {
        byte[] data;
        try {
            Path path = Paths.get(this.fileStorageLocation.resolve(fileKey).normalize().toString());
            data = Files.readAllBytes(path);
        } catch (IOException ex) {
            throw new IOException("IOE Error Message= " + ex.getMessage());
        }
        return data;
    }
}
