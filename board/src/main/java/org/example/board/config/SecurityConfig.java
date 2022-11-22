package org.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 기존에 WebSecurityConfigurerAdapter 가 SecurityFilterChain 으로 바뀌었다.
 * TODO : 참고 : https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 */
//@EnableWebSecurity // spring boot + security 에서는 autoConfiguration 안에 들어가 있기에 생략가능
@Configuration
public class SecurityConfig {

    // 기본적으로 DefaultLoginPageGeneratingFilter, DefaultLogoutPageGeneratingFilter 를 통해 기본 로그인페이지 제공해준다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(auth -> auth.anyRequest().permitAll())
                .formLogin()
                .and().build();
    }
}
