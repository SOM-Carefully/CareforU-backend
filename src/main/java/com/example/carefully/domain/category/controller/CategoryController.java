package com.example.carefully.domain.category.controller;

import com.example.carefully.domain.category.dto.CategoryDto;
import com.example.carefully.domain.category.service.CategoryService;
import com.example.carefully.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.post.dto.PostResponseMessage.*;

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
    public ResponseEntity<BaseResponse<CategoryDto.SearchResponse>> getCategoryList() {
        return ResponseEntity.ok(BaseResponse.create(
                GET_CATEGORY_LIST_SUCCESS.getMessage(), categoryService.searchCategoryList()));
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<BaseResponse<String>> updateCategory(@PathVariable(name = "categoryId") Long categoryId,
                                                               @RequestBody CategoryDto.UpdateRequest request) {
        categoryService.updateCategory(categoryId, request);
        return ResponseEntity.ok(BaseResponse.create(UPDATE_CATEGORY_SUCCESS.getMessage()));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<BaseResponse<String>> updateCategory(@PathVariable(name = "categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_CATEGORY_SUCCESS.getMessage()));
    }
}
