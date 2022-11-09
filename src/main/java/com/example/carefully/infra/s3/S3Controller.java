package com.example.carefully.infra.s3;

import com.example.carefully.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.carefully.domain.post.dto.PostResponseMessage.*;

@RestController
@RequestMapping("api/v1/file")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping
    public BaseResponse<List<String>> fileUpload(@RequestPart(value = "file") List<MultipartFile> multipartFileList) {
        return BaseResponse.create(FILE_UPLOAD_SUCCESS.getMessage(), s3Service.uploadFile(multipartFileList));
    }
}
