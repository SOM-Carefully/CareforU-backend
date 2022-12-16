package com.example.carefully.domain.category.controller;

import com.example.carefully.domain.category.dto.CategoryDto;
import com.example.carefully.domain.category.service.CategoryService;
import com.example.carefully.global.dto.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.post.dto.PostResponseMessage.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "카테고리 생성", notes = "관리자가 카테고리를 생성하는 API")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BaseResponse<CategoryDto.CreateResponse>> createCategory(@RequestBody CategoryDto.CreateRequest request) {
        return ResponseEntity.ok(BaseResponse.create(
                CREATE_CATEGORY_SUCCESS.getMessage(), categoryService.createCategory(request)));
    }

    @ApiOperation(value = "카테고리 리스트 조회", notes = "카테고리 리스트를 조회하는 API")
    @GetMapping
    public ResponseEntity<BaseResponse<CategoryDto.SearchResponse>> getCategoryList() {
        return ResponseEntity.ok(BaseResponse.create(
                GET_CATEGORY_LIST_SUCCESS.getMessage(), categoryService.searchCategoryList()));
    }

    @ApiOperation(value = "카테고리 수정", notes = "관리자가 카테고리를 수정하는 API")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{categoryId}")
    public ResponseEntity<BaseResponse<String>> updateCategory(@PathVariable(name = "categoryId") Long categoryId,
                                                               @RequestBody CategoryDto.UpdateRequest request) {
        categoryService.updateCategory(categoryId, request);
        return ResponseEntity.ok(BaseResponse.create(UPDATE_CATEGORY_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "카테고리 삭제", notes = "관리자가 카테고리를 삭제하는 API")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<BaseResponse<String>> deleteCategory(@PathVariable(name = "categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_CATEGORY_SUCCESS.getMessage()));
    }
}
