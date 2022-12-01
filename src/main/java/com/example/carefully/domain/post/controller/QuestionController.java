package com.example.carefully.domain.post.controller;

import com.example.carefully.domain.post.dto.QuestionDto;
import com.example.carefully.domain.post.service.QuestionServiceImpl;
import com.example.carefully.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.post.dto.PostResponseMessage.UPDATE_QUESTION_SUCCESS;
import static com.example.carefully.domain.post.dto.PostResponseMessage.UPLOAD_QUESTION_SUCCESS;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionServiceImpl questionService;

    @PostMapping
    public ResponseEntity<BaseResponse<QuestionDto.CreateResponse>> createQuestion(@RequestBody QuestionDto.CreateRequest createRequest) {
        return ResponseEntity.ok(BaseResponse.create(UPLOAD_QUESTION_SUCCESS.getMessage(), questionService.createNewQuestion(createRequest)));
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity<BaseResponse<String>> updateQuestion(@PathVariable("questionId") Long questionId,
                                                               @RequestBody QuestionDto.UpdateRequest updateRequest) {
        questionService.updateQuestion(updateRequest, questionId);
        return ResponseEntity.ok(BaseResponse.create(UPDATE_QUESTION_SUCCESS.getMessage()));
    }
}
