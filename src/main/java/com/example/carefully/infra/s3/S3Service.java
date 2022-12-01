package com.example.carefully.infra.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.carefully.domain.post.exception.FileEmptyException;
import com.example.carefully.domain.post.exception.FileUploadFailException;
import com.example.carefully.global.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public List<String> uploadFile(List<MultipartFile> multipartFile) {
        List<String> fileNameList = new ArrayList<>();
        validateFileExists(multipartFile);

        multipartFile.forEach(file -> {
            String fileName = FileUtils.createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetaData = createObjectMetaData(file);
            uploadToS3(file, fileName, objectMetaData);
            fileNameList.add(String.valueOf(amazonS3Client.getUrl(bucket, fileName)));
        });
        return fileNameList;
    }

    private void uploadToS3(MultipartFile file, String fileName, ObjectMetadata objectMetaData) {
        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, fileName, inputStream, objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            log.info(e.getMessage());
            throw new FileUploadFailException();
        }
    }

    private ObjectMetadata createObjectMetaData(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        return objectMetadata;
    }

    private void validateFileExists(List<MultipartFile> multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new FileEmptyException();
        }
    }

    public void deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucket, key);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }
}
