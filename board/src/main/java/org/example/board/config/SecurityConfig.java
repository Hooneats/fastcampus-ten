package org.example.board.config;

import org.example.board.dto.security.BoardPrincipal;
import org.example.board.dto.security.KakaoOAuth2Response;
import org.example.board.service.UserAccountService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 기존에 WebSecurityConfigurerAdapter 가 SecurityFilterChain 으로 바뀌었다.
 * TODO : 참고 : https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 */
//@EnableWebSecurity // spring boot + security 에서는 autoConfiguration 안에 들어가 있기에 생략가능
@Configuration
public class SecurityConfig {

    // 기본적으로 DefaultLoginPageGeneratingFilter, DefaultLogoutPageGeneratingFilter 를 통해 기본 로그인페이지 제공해준다.
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService
    ) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // 정적 리소스 열어준다.
                        .mvcMatchers( // mvcMatchers 는 기존의 antMatchers 보다 스프링의 여러가지 패턴 매칭 기능을 더 제공해준다.
                                HttpMethod.GET, // GET 맵핑에 대해
                                "/", // 루트 페이지
                                "/articles", // article 페이지
                                "/articles/search-hashtag" // article 의 hashtag 페이지
                        ).permitAll()
                        .anyRequest().authenticated() // 다른 리퀘스트는 인증 검사해라
                )
                .formLogin(withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .oauth2Login(oAuth -> oAuth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)
                        )
                )
                .build();
    }

//    기존 방식의 정적 리소스를 열어준다.
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // static : resource, css - js
//        // return web -> web.ignoring().antMatchers("/css");
//        // 스프링이 정적 리소스를 담는 위치를 간단하게 제공해준다.
//        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    } // ----> 이러한 설정은 권장하지 않는다 그 이유는 정적 리소스 쪽에 보안적용이 안되기 때문이다. 때문에 이러한 부분도 securityFilterChain 에서 사용하라

    // 사용자 인증 정보를 가져오는 @Bean UserDetailsService 에서 UserDetails 를 가져와야한다.
    @Bean
    public UserDetailsService userDetailsService(UserAccountService userAccountService) {
        return username -> userAccountService
                .searchUser(username)
                .map(BoardPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: " + username));
    }

    /**
     * <p>
     * OAuth 2.0 기술을 이용한 인증 정보를 처리한다.
     * 카카오 인증 방식을 선택.
     *
     * <p>
     * TODO: 카카오 도메인에 결합되어 있는 코드. 확장을 고려하면 별도 인증 처리 서비스 클래스로 분리하는 것이 좋지만, 현재 다른 OAuth 인증 플랫폼을 사용할 예정이 없어 이렇게 마무리한다.
     *
     * @param userAccountService  게시판 서비스의 사용자 계정을 다루는 서비스 로직
     * @param passwordEncoder 패스워드 암호화 도구
     * @return {@link OAuth2UserService} OAuth2 인증 사용자 정보를 읽어들이고 처리하는 서비스 인스턴스 반환
     */
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(
            UserAccountService userAccountService,
            PasswordEncoder passwordEncoder
    ) {
        final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            KakaoOAuth2Response kakaoResponse = KakaoOAuth2Response.from(oAuth2User.getAttributes());
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            String providerId = String.valueOf(kakaoResponse.id());
            String username = registrationId + "_" + providerId;
            String dummyPassword = passwordEncoder.encode("{bcrypt}" + UUID.randomUUID());

            return userAccountService.searchUser(username)
                    .map(BoardPrincipal::from)
                    .orElseGet(() ->
                            BoardPrincipal.from(
                                    userAccountService.saveUser(
                                            username,
                                            dummyPassword,
                                            kakaoResponse.email(),
                                            kakaoResponse.nickname(),
                                            null
                                    )
                            )
                    );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
