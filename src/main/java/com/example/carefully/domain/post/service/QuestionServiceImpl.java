package com.example.carefully.domain.post.service;

import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.post.domain.PostRole;
import com.example.carefully.domain.post.dto.QuestionDto;
import com.example.carefully.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final PostRepository postRepository;
    private final Long tempUserId = 1L;

    @Override
    @Transactional
    public QuestionDto.CreateResponse createNewQuestion(QuestionDto.CreateRequest request, String category) {
        Post question = postRepository.save(request.toEntity(PostRole.QUEST, tempUserId));
        return new QuestionDto.CreateResponse(question.getId());
    }
}
