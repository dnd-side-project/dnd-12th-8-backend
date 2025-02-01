package com.dnd.demo.common;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class TestController {

    private final TestRepository testRepository;

    @Operation(summary = "test", description = "이 API는 테스트 API 입니다.")
    @GetMapping
    public String test() {
        return "123";
    }

    @GetMapping("/test")
    public List<Test> findAll() {
        return testRepository.findAll();
    }
}
