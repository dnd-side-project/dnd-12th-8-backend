package com.dnd.demo.global.auth.config;

import com.dnd.demo.global.auth.filter.JwtAuthenticationFilter;
import com.dnd.demo.global.auth.handler.OAuthAccessDeniedHandler;
import com.dnd.demo.global.auth.handler.OAuthSuccessHandler;
import com.dnd.demo.global.auth.service.CustomUserDetailService;
import com.dnd.demo.global.auth.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final OAuthAccessDeniedHandler oAuthAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
        JwtTokenProvider jwtTokenProvider) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(
                        "/",
                        "/login",
                        "/members/onboarding",
                        "/oauth2/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                    ).permitAll()
                    .requestMatchers("/test").hasAnyRole("admin", "user")
                    .anyRequest().permitAll();
            })
            .oauth2Login(oauth2 ->
                oauth2
                    .userInfoEndpoint(endpoint ->
                        endpoint.userService(customUserDetailService))
                    .successHandler(oAuthSuccessHandler)
            )
            .exceptionHandling(exception -> exception.accessDeniedHandler(oAuthAccessDeniedHandler))
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

