package org.example.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 본 버전은 JDK 17 버전 기준으로 작성 따라서 프로젝트 구조에 본 모듈의 main 만 JDK 17 로 변경
 */
@SpringBootApplication
public class BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class);
    }
}
