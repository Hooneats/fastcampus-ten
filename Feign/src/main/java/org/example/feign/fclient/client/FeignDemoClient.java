package org.example.feign.fclient.client;

import org.example.feign.fclient.common.dto.BaseRequestInfo;
import org.example.feign.fclient.common.dto.BaseResponseInfo;
import org.example.feign.fclient.config.FeignDemoConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "demo-client", //pk 값
        url = "${feign.url.prefix}", // target url
        configuration = FeignDemoConfig.class
)
public interface FeignDemoClient {

    @GetMapping("/get")
    ResponseEntity<BaseResponseInfo> callGet(
            @RequestHeader("CustomHeaderName") String customHeader,
            @RequestParam("name") String name,
            @RequestParam("age") Long age
    );

    @PostMapping("/post")
    ResponseEntity<BaseResponseInfo> callPost(
            @RequestHeader("CustomHeaderName") String customHeader,
            @RequestBody BaseRequestInfo baseRequestInfo
            );

    @GetMapping("/error")
    ResponseEntity<BaseResponseInfo> callErrorDecoder();
}
