package com.example.carefully.domain.comment.repository;

import com.example.carefully.domain.comment.domain.Comment;
import com.example.carefully.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUser(Long commentId, User user);
}
