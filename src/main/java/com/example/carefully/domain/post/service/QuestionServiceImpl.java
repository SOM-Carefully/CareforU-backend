package com.example.carefully.domain.post.service;

import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.post.domain.PostRole;
import com.example.carefully.domain.post.dto.QuestionDto;
import com.example.carefully.domain.post.exception.PostEmptyException;
import com.example.carefully.domain.post.repository.PostRepository;
import com.example.carefully.global.dto.SliceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    public QuestionDto.CreateResponse createNewQuestion(QuestionDto.CreateRequest request) {
        Post question = postRepository.save(request.toEntity(PostRole.QUEST, tempUserId));
        return new QuestionDto.CreateResponse(question.getId());
    }

    @Override
    @Transactional
    public void updateQuestion(QuestionDto.UpdateRequest request, Long questionId) {
        Post question = postRepository.findById(questionId).orElseThrow(PostEmptyException::new);
        question.updateQuest(request.getTitle(), request.getContent(), request.isLocked());
    }

    @Override
    public QuestionDto.SearchResponse searchQuestionDetail(Long questionId) {
        Post question = postRepository.findById(questionId).orElseThrow(PostEmptyException::new);
        return QuestionDto.SearchResponse.create(question);
    }

    @Override
    public SliceDto<QuestionDto.SearchResponse> searchQuestionList(Pageable pageable) {
        Slice<QuestionDto.SearchResponse> sliceDto = postRepository.findAllByPostRoleOrderByCreatedAtDesc(pageable, PostRole.QUEST)
                .map(QuestionDto.SearchResponse::create);

        return SliceDto.create(sliceDto);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long questionId) {
        Post question = postRepository.findById(questionId).orElseThrow(PostEmptyException::new);
        postRepository.delete(question);
    }
}
