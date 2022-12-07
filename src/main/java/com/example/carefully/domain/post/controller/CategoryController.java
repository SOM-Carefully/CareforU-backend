package com.example.carefully.domain.post.controller;

import com.example.carefully.domain.post.dto.CategoryDto;
import com.example.carefully.domain.post.service.CategoryService;
import com.example.carefully.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.post.dto.PostResponseMessage.CREATE_CATEGORY_SUCCESS;
import static com.example.carefully.domain.post.dto.PostResponseMessage.GET_CATEGORY_LIST_SUCCESS;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<BaseResponse<CategoryDto.CreateResponse>> createCategory(@RequestBody CategoryDto.CreateRequest request) {
        return ResponseEntity.ok(BaseResponse.create(
                CREATE_CATEGORY_SUCCESS.getMessage(), categoryService.createCategory(request)));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<CategoryDto.SearchResponse>> createCategory() {
        return ResponseEntity.ok(BaseResponse.create(
                GET_CATEGORY_LIST_SUCCESS.getMessage(), categoryService.searchCategoryList()));
    }
}
