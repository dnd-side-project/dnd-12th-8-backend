package com.dnd.demo.global.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/")
@RestController
public class TestController {

	@Operation(summary = "test", description = "이 API는 테스트 API 입니다.")
	@GetMapping
	public String test() {
		return "123";
	}
}
