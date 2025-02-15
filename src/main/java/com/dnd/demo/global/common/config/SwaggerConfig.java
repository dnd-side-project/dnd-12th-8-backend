package com.dnd.demo.global.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${security.auth.header}")
    private String authHeader;

    @Bean
    public OpenAPI openAPI() {
        // security schema 정의
        SecurityScheme securityScheme = new SecurityScheme()
          .type(Type.HTTP)
          .in(SecurityScheme.In.HEADER)
          .bearerFormat("JWT")
          .scheme("bearer")
          .name(authHeader);

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");

        return new OpenAPI()
          .components(new Components().addSecuritySchemes("BearerAuth", securityScheme)) // 컴포넌트 설정
          .info(apiInfo())
          .addSecurityItem(securityRequirement);
    }

    private Info apiInfo() {
        return new Info()
          .title("98 percents API 명세서") // API 타이틀
          .description("") // API 설명
          .version("v1");
    }
}
