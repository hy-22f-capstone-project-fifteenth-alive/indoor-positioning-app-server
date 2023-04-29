package net.jaram.indoornavigation.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import net.jaram.indoornavigation.dto.UploadFileResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Objects;

@Service
@Profile("prod")
@RequiredArgsConstructor
public class FileS3ServiceImpl implements FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

    @Override
    public UploadFileResponse upload(MultipartFile file, String fileName) throws IOException{
        return uploadOnS3(file);
    }
    @Override
    public byte[] download(String fileKey) throws IOException {
        byte[] content = null;
        final S3Object s3Object = amazonS3Client.getObject(bucket, fileKey);
        final S3ObjectInputStream stream = s3Object.getObjectContent();
        try {
            content = IOUtils.toByteArray(stream);
            s3Object.close();
        } catch(final IOException ex) {
            throw new IOException("IO Error Message= " + ex.getMessage());
        }
        return content;
    }

    private UploadFileResponse uploadOnS3(MultipartFile uploadFile) throws IOException {
        UploadFileResponse uploadFileResponse = null;

        final TransferManager transferManager = new TransferManager(this.amazonS3Client);
        final PutObjectRequest request = new PutObjectRequest(bucket, uploadFile.getOriginalFilename(), uploadFile.getInputStream(), getMetadataValue(uploadFile));
        final Upload upload =  transferManager.upload(request);

        try {
            UploadResult result = upload.waitForUploadResult();
            if (upload.getState().name().equals("Completed")){
                uploadFileResponse = new UploadFileResponse( result.getKey(),result.getETag(), getMetadataValue(uploadFile).getContentType(),uploadFile.getSize());
            } else {
                throw new Exception("Upload fail");
            }
        } catch (AmazonClientException amazonClientException) {
            System.out.println("amazonClientException " + amazonClientException);
        } catch (InterruptedException ex) {
            System.out.println("InterruptedException " + ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  uploadFileResponse;
    }

    private ObjectMetadata getMetadataValue(MultipartFile uploadFile){
        String fileName = uploadFile.getOriginalFilename();
        long fileSize = uploadFile.getSize();
        String ext = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf(".") + 1);

        ObjectMetadata objectMetadata = new ObjectMetadata();

        if (ext.equals("jpg")){
            objectMetadata.setContentType(MediaType.IMAGE_JPEG_VALUE);
        }else if (ext.equals("png")){
            objectMetadata.setContentType(MediaType.IMAGE_PNG_VALUE);
        }else {
            objectMetadata.setContentType("application/octet-stream");
        }
        objectMetadata.setContentLength(fileSize);
        return objectMetadata;
    }

}