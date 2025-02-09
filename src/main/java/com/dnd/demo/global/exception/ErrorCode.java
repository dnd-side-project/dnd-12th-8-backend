package com.dnd.demo.global.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;


@Getter
public enum ErrorCode {

    //Member
    MEMBER_NOT_FOUND(NOT_FOUND, "사용자가 존재하지 않습니다"),

    ;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
