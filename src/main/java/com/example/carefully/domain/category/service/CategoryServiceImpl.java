package com.example.carefully.domain.category.service;

import com.example.carefully.domain.category.domain.Category;
import com.example.carefully.domain.category.dto.CategoryDto;
import com.example.carefully.domain.category.exception.CategoryEmptyException;
import com.example.carefully.domain.category.repository.CategoryRepository;
import com.example.carefully.domain.category.service.CategoryService;
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

    /**
     * 게시판 카테고리를 생성한다.   EX) 비밀/만남/토론
     * 유료회원 카테고리는 어플리케이션 시작 시 자동으로 생성된다.
     *
     * @param request 생성에 필요한 카테고리 명칭
     * @return 새로 생성된 카테고리 ID
     */
    @Override
    @Transactional
    public CategoryDto.CreateResponse createCategory(CategoryDto.CreateRequest request) {
        Category category = categoryRepository.save(request.toEntity());
        return new CategoryDto.CreateResponse(category.getId());
    }

    /**
     * 게시판에 어떠한 카테고리 있는지 전체 리스트를 조회한다.
     *
     * @return 카테고리 ID와 이름
     */
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

    /**
     * 카테고리 명칭을 수정한다.
     *
     * @param categoryId 수정하려는 카테고리 ID
     * @param request 새롭게 수정하고 싶은 카테고리 명칭
     */
    @Override
    @Transactional
    public void updateCategory(Long categoryId, CategoryDto.UpdateRequest request) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryEmptyException::new);
        category.updateName(request.getCategoryName());
    }

    /**
     * 카테고리를 삭제한다.
     *
     * @param categoryId 삭제하려는 카테고리 ID
     */
    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
