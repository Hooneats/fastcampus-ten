package org.example.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@ConfigurationPropertiesScan
@SpringBootApplication
public class BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class);
    }
}

/**
 * - 배포
 * 헤로쿠 (무료 클라우드)
 * https://www.heroku.com
 * mysql 의 8.0 버전을 지원하는 JawsDB 로 설정
 *
 * - 헤로쿠 로징
 * 헤로쿠 CLI 사용
 *
 * - gradle 빌드하는데 필요한 프로파일 파일이 있다.
 * Profile 파일 참고
 * - system.properties 파일 또한 필요하다.
 * system.properties 파일 참고
 * - build.gradle 에 헤로쿠 설정 추가
 * build.gradle 참고
 *
 * - 헤고쿠 명령어
 * heroku create
 * heroku git:remote --app (헤로쿠 프로젝트 이름)
 * git push heroku main
 * heroku ps
 * heroku open
 * heroku logs --tail
 *
 */
