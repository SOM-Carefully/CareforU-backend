package com.example.carefully.domain.quest.exception;

import com.example.carefully.global.error.common.ErrorCode;
import com.example.carefully.global.error.exception.EntityNotFoundException;

public class QuestEmptyException extends EntityNotFoundException {
    public QuestEmptyException() {
        super(ErrorCode.QUEST_NOT_FOUND);
    }
}
