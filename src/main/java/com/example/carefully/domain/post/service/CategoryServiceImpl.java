package com.example.carefully.domain.post.service;

import com.example.carefully.domain.post.domain.Category;
import com.example.carefully.domain.post.dto.CategoryDto;
import com.example.carefully.domain.post.exception.CategoryEmptyException;
import com.example.carefully.domain.post.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public CategoryDto.SearchResponse searchCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto.CategoryResponse> mappedResponse = categories.stream()
                .map(c -> CategoryDto.CategoryResponse.builder()
                        .categoryId(c.getId())
                        .categoryName(c.getName()).build())
                .collect(Collectors.toList());

        return new CategoryDto.SearchResponse(mappedResponse);
    }

    @Override
    @Transactional
    public void updateCategory(Long categoryId, CategoryDto.UpdateRequest request) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryEmptyException::new);
        category.updateName(request.getCategoryName());
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
