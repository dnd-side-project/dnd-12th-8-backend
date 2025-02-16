package com.dnd.demo.domain.member.dto;

import lombok.Builder;

@Builder
public record TokenDto(String grantType, String accessToken, String refreshToken) {

}
