package org.example.async.feign.logger;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static feign.Util.*;

@Slf4j
@RequiredArgsConstructor
public class FeignCustomLogger extends Logger {

    private static final int DEFAULT_SLOW_API_TIME = 3_000;
    private static final String SLOW_API_NOTICE = "Slow API";

    @Override
    protected void log(String configKey, String format, Object... args) {
        // log 를 어떤 형식으로 남길지 정해준다.
        // 아래처름 만들면 다른데서 log 를 사용할 때 아래 정의가 동작된다.
        System.out.println(String.format(methodTag(configKey) + format, args));
//        log.info(String.format(methodTag(configKey) + format + "%n", args));
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        // request 만 핸들링 가능
        System.out.println("[logRequest] : " + request);
//        super.logRequest(configKey, logLevel, request);
    }

    /**
     * 가장 중요한 메서드 사실상 이 메서드로 위의 두가지 메서드 둘다 커버 가능
     */
    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        // request , response 둘다 핸들링 가능

        // protocol 버전
//        String protocolVersion = resolveProtocolVersion(response.protocolVersion());
//        String reason = (response.reason() != null
//                && logLevel.compareTo(Level.NONE) > 0)
//                ? " " + response.reason()
//                : "";
//        int status = response.status();
//        log(configKey, "<-- %s %s%s (%sns)", protocolVersion, status, reason, elapsedTime);
//
//        if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {
//
//        }
//      그냥 아래와 같이 미리 Logger 에 정의된거 써보자
        /**
         * 로그에서 중요하게 봐야할 것은 elapsedTime 이다.
         */

        String protocolVersion = resolveProtocolVersion(response.protocolVersion());
        String reason =
                response.reason() != null && logLevel.compareTo(Level.NONE) > 0 ? " " + response.reason()
                        : "";
        int status = response.status();
        log(configKey, "<--- %s %s%s (%sms)", protocolVersion, status, reason, elapsedTime);
        if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {

            for (String field : response.headers().keySet()) {
                if (shouldLogResponseHeader(field)) {
                    for (String value : valuesOrEmpty(response.headers(), field)) {
                        log(configKey, "%s: %s", field, value);
                    }
                }
            }

            int bodyLength = 0;
            if (response.body() != null && !(status == 204 || status == 205)) {
                // HTTP 204 No Content "...response MUST NOT include a message-body"
                // HTTP 205 Reset Content "...response MUST NOT include an entity"
                if (logLevel.ordinal() >= Level.FULL.ordinal()) {
                    log(configKey, ""); // CRLF
                }
                byte[] bodyData = Util.toByteArray(response.body().asInputStream());
                bodyLength = bodyData.length;
                if (logLevel.ordinal() >= Level.FULL.ordinal() && bodyLength > 0) {
                    log(configKey, "%s", decodeOrDefault(bodyData, UTF_8, "Binary data"));
                }

                // 예를 들어 elapsedTime 이 오래걸리면
                if (elapsedTime > DEFAULT_SLOW_API_TIME) {
                    // 적절한 대응 - ex)알람등
                    log(configKey, "[%s] elapsedTime : %s", SLOW_API_NOTICE, elapsedTime);
                }

                log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
                return response.toBuilder().body(bodyData).build();
            } else {
                log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
            }
        }
        return response;

//        return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
    }
}
