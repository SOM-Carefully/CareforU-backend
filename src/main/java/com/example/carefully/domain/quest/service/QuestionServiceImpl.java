package com.example.carefully.domain.quest.service;

import com.example.carefully.domain.quest.domain.Quest;
import com.example.carefully.domain.quest.dto.QuestDto;
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
    private final Long tempAdminId = 2L;

    @Override
    @Transactional
    public QuestDto.CreateResponse createNewQuestion(QuestDto.CreateRequest request) {
        Quest question = questRepository.save(request.toEntity(tempUserId));
        return new QuestDto.CreateResponse(question.getId());
    }

    @Override
    @Transactional
    public void updateQuestion(QuestDto.UpdateRequest request, Long questionId) {
        Quest question = findQuestById(questionId);
        question.updateQuest(request.getTitle(), request.getContent(), request.isLocked());
    }

    @Override
    public QuestDto.SearchResponse searchQuestionDetail(Long questionId) {
        Quest question = findQuestById(questionId);
        return QuestDto.SearchResponse.create(question);
    }

    @Override
    public SliceDto<QuestDto.SearchResponse> searchQuestionList(Pageable pageable) {
        Slice<QuestDto.SearchResponse> sliceDto = questRepository.findAllByOrderByCreatedAtDesc(pageable)
                        .map(QuestDto.SearchResponse::create);

        return SliceDto.create(sliceDto);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long questionId) {
        Quest question = findQuestById(questionId);
        questRepository.delete(question);
    }

    @Override
    @Transactional
    public void registerAnswer(QuestDto.AnswerRequest request, Long questionId) {
        Quest quest = findQuestById(questionId);
        quest.registerAns(tempAdminId, request.getContent());
    }

    private Quest findQuestById(Long questionId) {
        return questRepository.findById(questionId).orElseThrow(QuestEmptyException::new);
    }
}
