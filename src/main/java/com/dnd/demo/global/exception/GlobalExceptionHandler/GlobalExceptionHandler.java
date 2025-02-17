package com.dnd.demo.global.exception.GlobalExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.dnd.demo.global.exception.CustomErrorResponse;
import com.dnd.demo.global.exception.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final String FIELD_VALIDATION_ERROR = "FIELD_VALIDATION_ERROR";
    private final String JSON_PARSE_ERROR = "JSON_PARSE_ERROR";

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<CustomErrorResponse> handleCustomException(CustomException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
            ex.getHttpStatus().name(),
            ex.getErrorCode().name(),
            ex.getErrorMessage());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CustomErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);
        CustomErrorResponse errorResponse = new CustomErrorResponse(
            HttpStatus.BAD_REQUEST.name(),
            FIELD_VALIDATION_ERROR,
            fieldError.getDefaultMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse> handleJsonParseException(HttpMessageNotReadableException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
            HttpStatus.BAD_REQUEST.name(),
            JSON_PARSE_ERROR,
            "잘못된 JSON 요청 형식입니다."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
