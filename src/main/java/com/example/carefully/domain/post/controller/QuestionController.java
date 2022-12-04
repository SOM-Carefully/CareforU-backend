package com.example.carefully.domain.post.controller;

import com.example.carefully.domain.post.dto.QuestionDto;
import com.example.carefully.domain.post.service.QuestionServiceImpl;
import com.example.carefully.global.dto.BaseResponse;
import com.example.carefully.global.dto.SliceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.post.dto.PostResponseMessage.*;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionServiceImpl questionService;

    @PostMapping
    public ResponseEntity<BaseResponse<QuestionDto.CreateResponse>> createQuestion(@RequestBody QuestionDto.CreateRequest createRequest) {
        return ResponseEntity.ok(BaseResponse.create(
                UPLOAD_QUESTION_SUCCESS.getMessage(), questionService.createNewQuestion(createRequest)));
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity<BaseResponse<String>> updateQuestion(@PathVariable("questionId") Long questionId,
                                                               @RequestBody QuestionDto.UpdateRequest updateRequest) {
        questionService.updateQuestion(updateRequest, questionId);
        return ResponseEntity.ok(BaseResponse.create(UPDATE_QUESTION_SUCCESS.getMessage()));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<BaseResponse<QuestionDto.SearchResponse>> searchQuestionDetails(@PathVariable("questionId") Long questionId) {
        return ResponseEntity.ok(BaseResponse.create(
                GET_QUESTION_DETAIL_SUCCESS.getMessage(), questionService.searchQuestionDetail(questionId)));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<SliceDto<QuestionDto.SearchResponse>>> searchQuestionList(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(BaseResponse.create(
                GET_QUESTION_LIST_SUCCESS.getMessage(), questionService.searchQuestionList(pageable)));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<BaseResponse<String>> deleteQuestion(@PathVariable("questionId") Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_QUESTION_SUCCESS.getMessage()));
    }
}
