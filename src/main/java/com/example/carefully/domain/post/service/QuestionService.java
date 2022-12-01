package com.example.carefully.domain.post.service;

import com.example.carefully.domain.post.dto.QuestionDto;

public interface QuestionService {

    QuestionDto.CreateResponse createNewQuestion(QuestionDto.CreateRequest request, String category);
}
