package com.example.carefully.domain.quest.service;

import com.example.carefully.domain.quest.domain.Quest;
import com.example.carefully.domain.quest.dto.QuestionDto;
import com.example.carefully.domain.quest.exception.QuestEmptyException;
import com.example.carefully.domain.quest.repository.QuestRepository;
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

    private final QuestRepository questRepository;
    private final Long tempUserId = 1L;

    @Override
    @Transactional
    public QuestionDto.CreateResponse createNewQuestion(QuestionDto.CreateRequest request) {
        Quest question = questRepository.save(request.toEntity(tempUserId));
        return new QuestionDto.CreateResponse(question.getId());
    }

    @Override
    @Transactional
    public void updateQuestion(QuestionDto.UpdateRequest request, Long questionId) {
        Quest question = questRepository.findById(questionId).orElseThrow(QuestEmptyException::new);
        question.updateQuest(request.getTitle(), request.getContent(), request.isLocked());
    }

    @Override
    public QuestionDto.SearchResponse searchQuestionDetail(Long questionId) {
        Quest question = questRepository.findById(questionId).orElseThrow(QuestEmptyException::new);
        return QuestionDto.SearchResponse.create(question);
    }

    @Override
    public SliceDto<QuestionDto.SearchResponse> searchQuestionList(Pageable pageable) {
        Slice<QuestionDto.SearchResponse> sliceDto = questRepository.findAllByOrderByCreatedAtDesc(pageable)
                        .map(QuestionDto.SearchResponse::create);

        return SliceDto.create(sliceDto);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long questionId) {
        Quest question = questRepository.findById(questionId).orElseThrow(QuestEmptyException::new);
        questRepository.delete(question);
    }
}
