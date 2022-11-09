package com.example.carefully.infra.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.carefully.domain.post.exception.FileEmptyException;
import com.example.carefully.domain.post.exception.FileUploadFailException;
import com.example.carefully.global.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
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

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch(IOException e) {
                throw new FileUploadFailException();
            }
            fileNameList.add(String.valueOf(amazonS3Client.getUrl(bucket, fileName)));
        });
        return fileNameList;
    }

    private void validateFileExists(List<MultipartFile> multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new FileEmptyException();
        }
    }
}
