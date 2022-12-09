package com.example.carefully.domain.post.service;

import com.example.carefully.domain.post.dto.QuestionDto;
import com.example.carefully.global.dto.SliceDto;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    QuestionDto.CreateResponse createNewQuestion(QuestionDto.CreateRequest request);
    void updateQuestion(QuestionDto.UpdateRequest request, Long questionId);
    QuestionDto.SearchResponse searchQuestionDetail(Long questionId);
    SliceDto<QuestionDto.SearchResponse> searchQuestionList(Pageable pageable);
    void deleteQuestion(Long questionId);
}
