package org.example.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncApplication.class);
    }

    /**
     * TODO : Feign Client
     *
     * - Connection/Read Timeout
     *      ㄴ 외부 서버와 통신시 Connection/Read Timeout 설정이 가능하다.
     *
     * - Feign Interceptor
     *      ㄴ 외부로 요청이 나가기 전에, 만약 공통적으로 처리해야하는 부분이 있다면
     *              Interceptor 를 재정의하여 처리가 가능하다
     *
     * - Feign Logger
     *      ㄴ Request / Response 등 운영을 하기 위한 적절한 Log 를 남길 수 있다.
     *
     * - Feign ErrorDecoder
     *      ㄴ 요청에 대해 정상 응답이 아닌 경우 핸들링이 가능하다.
     *          ex)외부 컴포넌트와 통신 시 정의해 놓은 예외 코드 일 경우엔 적절하게 핸들링하여 처리한다.
     */
}
