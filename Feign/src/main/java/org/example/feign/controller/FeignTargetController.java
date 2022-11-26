package org.example.feign.controller;

import lombok.RequiredArgsConstructor;
import org.example.feign.fclient.common.dto.BaseRequestInfo;
import org.example.feign.fclient.common.dto.BaseResponseInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("target_server")
@RequiredArgsConstructor
public class FeignTargetController {

    @GetMapping("/get")
    public BaseResponseInfo demoGet(
            @RequestHeader("CustomHeaderName") String header,
            @RequestParam("name") String name,
            @RequestParam("age") Long age
    ) throws InterruptedException {
        System.out.println("FeignTargetController : " + Thread.currentThread().getName());
        Thread.sleep(1000);
        return BaseResponseInfo.builder()
                .header(header)
                .name(name + "응답!")
                .age(age)
                .build();
    }


    @PostMapping("/post")
    public BaseResponseInfo demoPost(
            @RequestHeader("CustomHeaderName") String header,
            @RequestBody BaseRequestInfo baseRequestInfo
    ) throws InterruptedException {
        System.out.println("FeignTargetController : " + Thread.currentThread().getName());
        Thread.sleep(1000);
        return BaseResponseInfo.builder()
                .header(header)
                .name(baseRequestInfo.getName() + "응답!")
                .age(baseRequestInfo.getAge())
                .build();
    }

    @GetMapping("/error")
    public ResponseEntity<BaseResponseInfo> demoErrorDecoder() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
