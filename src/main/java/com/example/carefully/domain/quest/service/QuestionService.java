package com.example.carefully.domain.quest.service;

import com.example.carefully.domain.quest.dto.QuestDto;
import com.example.carefully.global.dto.SliceDto;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    QuestDto.CreateResponse createNewQuestion(QuestDto.CreateRequest request);
    void updateQuestion(QuestDto.UpdateRequest request, Long questionId);
    QuestDto.SearchResponse searchQuestionDetail(Long questionId);
    SliceDto<QuestDto.SearchResponse> searchQuestionList(Pageable pageable);
    void deleteQuestion(Long questionId);
    void registerAnswer(QuestDto.AnswerRequest request, Long questionId);
}
