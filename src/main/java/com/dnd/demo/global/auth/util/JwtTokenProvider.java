package com.dnd.demo.global.auth.util;

import com.dnd.demo.global.auth.dto.OAuthUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String key;
    private SecretKey secretKey;

    @Value("${security.jwt.token-expire-length}")
    private long tokenValidMillisecond;

    @Value("${security.auth.header}")
    private String authHeader;

    @Value("${security.claim.header}")
    private String claimHeader;

    @PostConstruct
    public void init() {
        this.secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
    }

    // TODO) claim에 어떤 값 들어갈지
    public String createToken(String memberId, Collection<? extends GrantedAuthority> role) {
        Date now = new Date();

        String accessToken = Jwts.builder()
          .claim(claimHeader, role)
          .subject(memberId)
          .issuedAt(now)
          .expiration(new Date(now.getTime() + tokenValidMillisecond))
          .signWith(secretKey, Jwts.SIG.HS256)
          .compact();

        return accessToken;
    }

    // 토큰 복호화하여 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(authHeader);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Filter 에 넘길 Authentication(권한 존재여부 체크)
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
          .getPayload();

        if (claims.get(claimHeader) == null) {
            throw new RuntimeException("권한이 없습니다.");
        }

        OAuthUserDetails userDetails = new OAuthUserDetails();
        userDetails.setMemberId(claims.getSubject());
        userDetails.setEmail(claims.get("email", String.class));

        Collection<? extends GrantedAuthority> authorities =
          Arrays.stream(claims.get(claimHeader).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .toList();

        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }
}
