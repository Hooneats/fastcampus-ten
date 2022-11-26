package org.example.logback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogBackApplication.class);
    }

    /**
     * <!--logback-spring.xml 파일을 만들었기에 스프링에서는 이제 기본으로 사용하던 로그를 쓰지 않는다.-->
     *    <incluede resource="org/springframework/boot/logging/logback/defaults.xml"/>
     *      를 넣어줌으로써 스프링부트가 사용하던 로깅을 다시 볼 수 있다.
     *      <incluede resource="logback-spring-${spring.profiles.active}.xml"/>
     *      를 넣어줌으로써 spring 의 profile 환경별로 다르게 작동 시킬 수 있다.
     *      그 후 logback-spring-local.xml 파일을 만들어
     *      <include resource="org/springframework/boot/logging/logback/console-appender.xml"/>
     *      를 추가해주면 해당경로 console-appender.xml 파일의 설정값이 logback-spring-local.xml 안에
     *      들어오는 방식으로 동작한다.
     */
}
