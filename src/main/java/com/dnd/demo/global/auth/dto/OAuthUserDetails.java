package com.dnd.demo.global.auth.dto;

import com.dnd.demo.domain.member.entity.Member;
import com.dnd.demo.domain.member.entity.MemberRole;
import com.dnd.demo.domain.project.entity.Category;
import com.dnd.demo.domain.project.entity.Job;
import com.dnd.demo.domain.project.entity.Level;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuthUserDetails implements OAuth2User {

    private String memberId;
    @Enumerated(EnumType.STRING)
    private Job job;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String email;
    private Integer points;
    private String memberName;
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
          new SimpleGrantedAuthority("ROLE_" + role)
        );
    }

    // id 리턴
    @Override
    public String getName() {
        return this.memberId;
    }

    public static OAuthUserDetails fromMember(Member member) {
        return OAuthUserDetails.builder().memberId(member.getMemberId())
          .memberName(member.getMemberName())
          .role(member.getRole())
          .build();
    }

    // TODO) 리팩토링
    public static OAuthUserDetails fromSocialLogin(String clientRegistrationId,
      OAuth2User oAuth2User) {
        String oAuthMemberId = null;
        String email = null;
        String memberName = null;
        String profileUrl = null;

        switch (clientRegistrationId) {
            case "kakao":
                oAuthMemberId = oAuth2User.getAttribute("id").toString();

                Map<String, String> properties = oAuth2User.getAttribute("properties");
                memberName = properties.get("nickname");
                profileUrl = properties.get("profile_image");
                break;
            case "google":
                oAuthMemberId = oAuth2User.getAttribute("sub");
                email = oAuth2User.getAttribute("email");
                memberName = oAuth2User.getAttribute("name");
                profileUrl = oAuth2User.getAttribute("picture");
                break;
            default:
                break;
        }
        return OAuthUserDetails.builder().memberId(oAuthMemberId)
          .email(email)
          .memberName(memberName)
          .profileUrl(profileUrl)
          .build();
    }

    public Member toEntity() {
        return Member.builder()
          .memberId(memberId)
          .memberName(memberName)
          .profileUrl(profileUrl)
          .email(email)
          .role(role)
          .build();
    }
}
