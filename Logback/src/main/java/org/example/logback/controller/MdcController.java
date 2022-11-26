package org.example.logback.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MDC 란 멀티쓰레드 환경에서 로그를 남길 때 사용하는 개념이다.
 * - 코드에서 로그백에 남길것을 저장해 로그백 xml 파일에서 동적으로 사용할 수 있다.
 * 쓰레드별로 MDC 안에 담긴 값을 관리한다.
 * 때문에 반드시 put 사용했으면 clear 해주자
 */
@Slf4j
@RestController
public class MdcController {

    @GetMapping("/mdc")
    public String mdc() {
        MDC.put("job", "dev");
        log.trace("log --> TRACE");
        log.debug("log --> DEBUG");
        log.info("log --> INFO");
        log.warn("log --> WARN");
        log.error("log --> ERROR");
        MDC.clear();

        return "mdc";
    }
}
