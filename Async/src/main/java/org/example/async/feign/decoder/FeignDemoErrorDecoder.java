package org.example.async.feign.decoder;


import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

/**
 * Decoder 의 경우 Feign Client 각 컴포넌트 마다 각각 돌아오는 응답의 정의라든지 에러코드가 다를수 있다.
 * 때문에 글로벌 빈등록보다 Client 단의 빈등록이 좋아보인다.
 */
public class FeignDemoErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default(); // 기본적인 Feign 에서 decode 코드들을 정의해 놓았다.
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus httpStatus = HttpStatus.resolve(response.status());

        if (httpStatus == HttpStatus.NOT_FOUND) {
            System.out.println("[FeignDemoErrorDecoder] Http Status = " + httpStatus);
            // 실무에서는 아래와같은 RuntimeException 이 아닌 ControllerAdvice Handling 이 가능한 Exception 을 던진다.
            throw new RuntimeException(String.format("[RuntimeException] Http Status is %s", httpStatus));
        }

        return errorDecoder.decode(methodKey, response);
    }
}
