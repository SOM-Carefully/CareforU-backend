package com.example.carefully.domain.post.service;

import com.example.carefully.domain.post.domain.Category;
import com.example.carefully.domain.post.dto.CategoryDto;
import com.example.carefully.domain.post.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryDto.CreateResponse createCategory(CategoryDto.CreateRequest request) {
        Category category = categoryRepository.save(request.toEntity());
        return new CategoryDto.CreateResponse(category.getId());
    }
}
