package com.example.carefully.domain.quest.service;

import com.example.carefully.domain.quest.domain.Quest;
import com.example.carefully.domain.quest.dto.QuestDto;
import com.example.carefully.domain.quest.exception.QuestEmptyException;
import com.example.carefully.domain.quest.repository.QuestRepository;
import com.example.carefully.domain.user.entity.User;
import com.example.carefully.domain.user.repository.UserRepository;
import com.example.carefully.global.dto.SliceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.carefully.global.utils.UserUtils.getCurrentUser;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestRepository questRepository;
    private final UserRepository userRepository;

    /**
     * 새로운 문의사항을 저장한다.
     *
     * @param request 문의 사항 제목 / 내용 / 공개 여부
     * @return 저장된 문의사항 ID
     */
    @Override
    @Transactional
    public QuestDto.CreateResponse createNewQuestion(QuestDto.CreateRequest request) {
        User currentUser = getCurrentUser(userRepository);
        Quest question = questRepository.save(request.toEntity(currentUser));
        return new QuestDto.CreateResponse(question.getId());
    }

    /**
     * 작성했던 문의사항을 수정한다.
     *
     * @param request 문의 사항 제목 / 내용 / 공개 여부
     * @param questionId 수정할 문의사항 ID
     * @throws QuestEmptyException 문의 사항이 존재하지 않는 경우
     */
    @Override
    @Transactional
    public void updateQuestion(QuestDto.UpdateRequest request, Long questionId) {
        Quest question = findQuestByIdAndUser(questionId);
        question.updateQuest(request.getTitle(), request.getContent(), request.isLocked());
    }

    /**
     * 문의사항 상세정보를 반환한다.
     *
     * @param questionId 조회 할 문의사항 ID
     * @return 문의사항 ID / 제목 / 내용 / 작성자 / 공개 여부 / 답변 내용 / 작성 날짜 반환
     * @throws QuestEmptyException 문의 사항이 존재하지 않는 경우
     */
    @Override
    public QuestDto.SearchResponse searchQuestionDetail(Long questionId) {
        Quest question = findQuestById(questionId);
        return QuestDto.SearchResponse.create(question);
    }

    /**
     * 모든 문의사항 리스트를 반환한다.
     *
     * @param pageable 한 페이지에 조회 할 문의사항 개수 및 페이징 정보
     * @return 문의사항 ID / 제목 / 내용 / 작성자 / 공개 여부 / 답변 내용 / 작성 날짜 / 페이징 정보
     */
    @Override
    public SliceDto<QuestDto.SearchResponse> searchQuestionList(Pageable pageable) {
        Slice<QuestDto.SearchResponse> sliceDto = questRepository.findAllByOrderByCreatedAtDesc(pageable)
                        .map(QuestDto.SearchResponse::create);

        return SliceDto.create(sliceDto);
    }

    /**
     * 작성했던 문의사항을 삭제한다.
     *
     * @param questionId 삭제할 문의사항 ID
     * @throws QuestEmptyException 문의 사항이 존재하지 않는 경우
     */
    @Override
    @Transactional
    public void deleteQuestion(Long questionId) {
        Quest question = findQuestByIdAndUser(questionId);
        questRepository.delete(question);
    }

    /**
     * 운영자가 문의사항에 대한 답변을 등록한다.
     *
     * @param request 운영자 답변 내용
     * @param questionId 답변을 등록할 문의사항 ID
     * @throws QuestEmptyException 문의 사항이 존재하지 않는 경우
     */
    @Override
    @Transactional
    public void registerAnswer(QuestDto.AnswerRequest request, Long questionId) {
        Quest quest = findQuestById(questionId);
        User adminUser = getCurrentUser(userRepository);
        quest.registerAns(adminUser, request.getContent());
    }

    private Quest findQuestById(Long questionId) {
        return questRepository.findById(questionId).orElseThrow(QuestEmptyException::new);
    }

    private Quest findQuestByIdAndUser(Long questionId) {
        return questRepository.findByIdAndUser(questionId, getCurrentUser(userRepository))
                .orElseThrow(QuestEmptyException::new);
    }
}
