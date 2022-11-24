package org.example.board.dto.security;

import org.example.board.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserDetails 를 구현시 Security 클래스 User 를 참고하면 좋다
 */
public record BoardPrincipal(
        String username, // userId
        String password,
        Collection<? extends GrantedAuthority> authorities, //권한
        String email,
        String nickname,
        String memo,
        Map<String, Object> oAuth2Attributes
) implements UserDetails, OAuth2User {

    public static BoardPrincipal of(String username, String password, String email, String nickname, String memo) {
        return BoardPrincipal.of(username, password, email, nickname, memo, Map.of());
    }

    public static BoardPrincipal of(String username, String password, String email, String nickname, String memo, Map<String, Object> oAuth2Attributes) {
        // 지금은 인증만 하고 권한을 다루고 있지 않아서 임의로 세팅한다.
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new BoardPrincipal(
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new) //GrantedAuthority 의 기본 구현체 SimpleGrantedAuthority
                        .collect(Collectors.toUnmodifiableSet())
                ,
                email,
                nickname,
                memo,
                oAuth2Attributes
        );
    }

    public static BoardPrincipal from(UserAccountDto dto) {
        return BoardPrincipal.of(
                dto.userId(),
                dto.userPassword(),
                dto.email(),
                dto.nickname(),
                dto.memo()
        );
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                username,
                password,
                email,
                nickname,
                memo
        );
    }


    // UserDetails 에 대한 오버라이딩 start
    @Override public String getUsername() { return username; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
    // UserDetails 에 대한 오버라이딩 ---- End

    @Override public Map<String, Object> getAttributes() { return oAuth2Attributes; }
    @Override public String getName() { return username; }

    public enum RoleType {
        USER("ROLE_USER"); // SpringSecurity 에서 제공하는 규칙 '_' 변경할 수도 있지만 굳이?!

        @Getter private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }

}
