package com.dnd.demo.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
          .components(new Components()) // 컴포넌트 설정
          .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
          .title("임시 title") // API 타이틀
          .description("임시 설명") // API 설명
          .version("1.0.0");
    }
}
