package com.dnd.demo.global.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //Member
    MEMBER_NOT_FOUND(NOT_FOUND, "사용자가 존재하지 않습니다"),
    UNAUTHORIZED_ACCESS(UNAUTHORIZED, "본인이 작성한 프로젝트 글이 아닙니다."),

    //Category
    CATEGORY_NOT_FOUND(NOT_FOUND,"카테고리가 존재하지 않습니다"),

    //Project
    PROJECT_FINAL_CREATE_ALREADY_UPLOAD(BAD_REQUEST, "이미 최종 업로드한 프로젝트입니다."),
    PROJECT_NOT_FOUND(NOT_FOUND,"프로젝트가 존재하지 않습니다."),
    TEMP_PROJECT_NOT_FOUND(NOT_FOUND,"해당 프로젝트는 임시 저장된 프로젝트가 아닙니다."),

    //FeedbackForm
    FEEDBACK_FORM(NOT_FOUND,"피드팩 폼이 존재하지 않습니다"),

    ;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}