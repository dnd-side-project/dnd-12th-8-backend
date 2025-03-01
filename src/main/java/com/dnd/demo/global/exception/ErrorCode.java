package com.dnd.demo.global.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //Member
    MEMBER_NOT_FOUND(NOT_FOUND, "사용자가 존재하지 않습니다"),
    UNAUTHORIZED_ACCESS(UNAUTHORIZED, "본인이 작성한 프로젝트 글이 아닙니다."),
    INSUFFICIENT_POINTS(BAD_REQUEST,"포인트가 부족합니다."),

    //Category
    CATEGORY_NOT_FOUND(NOT_FOUND,"카테고리가 존재하지 않습니다"),

    //Project
    PROJECT_FINAL_CREATE_ALREADY_UPLOAD(BAD_REQUEST, "이미 최종 업로드한 프로젝트입니다."),
    PROJECT_NOT_FOUND(NOT_FOUND,"프로젝트가 존재하지 않습니다."),
    TEMP_PROJECT_NOT_FOUND(NOT_FOUND,"해당 프로젝트는 임시 저장된 프로젝트가 아닙니다."),
    PROJECT_NOT_OPEN(BAD_REQUEST,"프로젝트가 OPEN 상태가 아닙니다"),

    //FeedbackForm
    FEEDBACK_FORM_NOT_FOUND(NOT_FOUND,"해당 프로젝트 ID에 대한 피드팩 폼이 존재하지 않습니다"),
    FEEDBACK_RESULT_NOT_FOUND(NOT_FOUND, "해당 프로젝트에 제출된 피드백 결과가 없습니다."),
    INVALID_FEEDBACK_QUESTION_ID(BAD_REQUEST, "해당 피드백 폼 내에 존재하지 않는 questionId가 포함되어 있습니다"),

    INVALID_IMAGE_TYPE(BAD_REQUEST, " 올바르지 않은 이미지 확장자 파일입니다."),

    QUIZ_ALREADY_COMPLETED(BAD_REQUEST,"이미 퀴즈를 완료하였습니다."),
    QUIZ_NOT_FOUND(NOT_FOUND,"사전 퀴즈가 존재하지 않습니다"),

    INVALID_QUIZ_PROJECT(UNAUTHORIZED,"해당 퀴즈는 해당 프로젝트에 대한 사전 퀴즈가 아닙니다."),

    KAKAO_SERVER_ERROR(BAD_REQUEST,"카카오 서버 에러입니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}