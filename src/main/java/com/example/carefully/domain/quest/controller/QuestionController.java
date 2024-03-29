package com.example.carefully.domain.quest.controller;

import com.example.carefully.domain.quest.dto.QuestDto;
import com.example.carefully.domain.quest.service.QuestionServiceImpl;
import com.example.carefully.global.dto.BaseResponse;
import com.example.carefully.global.dto.SliceDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.post.dto.PostResponseMessage.*;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionServiceImpl questionService;

    @ApiOperation(value = "문의사항 등록", notes = "문의사항 등록 API")
    @PostMapping
    public ResponseEntity<BaseResponse<QuestDto.CreateResponse>> createQuestion(@RequestBody QuestDto.CreateRequest createRequest) {
        return ResponseEntity.ok(BaseResponse.create(
                UPLOAD_QUESTION_SUCCESS.getMessage(), questionService.createNewQuestion(createRequest)));
    }

    @ApiOperation(value = "문의사항 수정", notes = "자신이 작성한 문의사항 수정 API")
    @PatchMapping("/{questionId}")
    public ResponseEntity<BaseResponse<String>> updateQuestion(@PathVariable("questionId") Long questionId,
                                                               @RequestBody QuestDto.UpdateRequest updateRequest) {
        questionService.updateQuestion(updateRequest, questionId);
        return ResponseEntity.ok(BaseResponse.create(UPDATE_QUESTION_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "문의사항 조회", notes = "문의사항 상세정보 조회 API")
    @GetMapping("/{questionId}")
    public ResponseEntity<BaseResponse<QuestDto.SearchResponse>> searchQuestionDetails(@PathVariable("questionId") Long questionId) {
        return ResponseEntity.ok(BaseResponse.create(
                GET_QUESTION_DETAIL_SUCCESS.getMessage(), questionService.searchQuestionDetail(questionId)));
    }

    @ApiOperation(value = "문의사항 리스트 조회", notes = "문의사항 전체 리스트 조회 API")
    @GetMapping
    public ResponseEntity<BaseResponse<SliceDto<QuestDto.SearchResponse>>> searchQuestionList(@PageableDefault(size = 100) Pageable pageable) {
        return ResponseEntity.ok(BaseResponse.create(
                GET_QUESTION_LIST_SUCCESS.getMessage(), questionService.searchQuestionList(pageable)));
    }

    @ApiOperation(value = "문의사항 삭제", notes = "자신이 작성한 문의사항 삭제 API")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<BaseResponse<String>> deleteQuestion(@PathVariable("questionId") Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_QUESTION_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "문의사항 답변 등록 및 수정", notes = "문의사항 답변 등록 및 수정 API")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{questionId}/answer")
    public ResponseEntity<BaseResponse<String>> answerQuestion(@PathVariable("questionId") Long questionId,
                                                                @RequestBody QuestDto.AnswerRequest request) {
        questionService.registerAnswer(request, questionId);
        return ResponseEntity.ok(BaseResponse.create(ANSWER_QUESTION_SUCCESS.getMessage()));
    }
}
