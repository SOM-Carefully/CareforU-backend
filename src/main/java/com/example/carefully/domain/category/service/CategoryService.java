package com.example.carefully.domain.category.service;

import com.example.carefully.domain.category.dto.CategoryDto;

public interface CategoryService {
    CategoryDto.CreateResponse createCategory(CategoryDto.CreateRequest request);
    CategoryDto.SearchResponse searchCategoryList();
    void updateCategory(Long categoryId, CategoryDto.UpdateRequest request);
    void deleteCategory(Long categoryId);
}
