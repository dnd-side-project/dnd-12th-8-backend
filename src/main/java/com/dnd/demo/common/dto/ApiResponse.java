package com.dnd.demo.common.dto;

public record ApiResponse<T>(int status, String message, T data) {
}