package com.example.carefully.domain.post.repository;

import com.example.carefully.domain.post.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
