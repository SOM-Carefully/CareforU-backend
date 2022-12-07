package com.example.carefully.domain.post.controller;

import com.example.carefully.domain.post.dto.CategoryDto;
import com.example.carefully.domain.post.service.CategoryService;
import com.example.carefully.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.carefully.domain.post.dto.PostResponseMessage.CREATE_CATEGORY_SUCCESS;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<BaseResponse<CategoryDto.CreateResponse>> createCategory(@RequestBody CategoryDto.CreateRequest request) {
        CategoryDto.CreateResponse category = categoryService.createCategory(request);
        return ResponseEntity.ok(BaseResponse.create(CREATE_CATEGORY_SUCCESS.getMessage(), category));
    }
}
