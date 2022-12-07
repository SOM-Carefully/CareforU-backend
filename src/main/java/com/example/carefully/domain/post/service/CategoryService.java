package com.example.carefully.domain.post.service;

import com.example.carefully.domain.post.dto.CategoryDto;

public interface CategoryService {
    CategoryDto.CreateResponse createCategory(CategoryDto.CreateRequest request);
    CategoryDto.SearchResponse searchCategoryList();
}
