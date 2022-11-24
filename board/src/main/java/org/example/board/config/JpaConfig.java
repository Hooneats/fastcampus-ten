package org.example.board.config;

import org.example.board.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    //스프링 시큐리티로 인증 기능 연동
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext()) // SecurityContext 를 가져온다.
                .map(SecurityContext::getAuthentication) // Authentication 을 가져온다.
                .filter(Authentication::isAuthenticated) // 인증된 사용자만 가져온다.
                .map(Authentication::getPrincipal) // 인증정보(UserDetails 를 Object 로 리턴한다.)를 가져온다.
                .map(BoardPrincipal.class::cast) // UserDetails 를 상속한 BoardPrincipal 로 캐스팅(Object 로 리턴되었기에 캐스팅해줌)
                .map(BoardPrincipal::getUsername); // username 을 리턴
    }

}
