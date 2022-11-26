package org.example.feign.controller;

import lombok.RequiredArgsConstructor;
import org.example.feign.sercvice.FeignDemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeignDemoController {

    private final FeignDemoService feignDemoService;

    /**
     * TODO 흐름
     *  GET -> :8080/get (FeignDemoController) 요청
     *          -> feignDemoService.get() 호출
     *                 | 비동기가 아니다!!!!!!!!!!!!!!
     *             -> feignDemoClient.callGet(headerName, name, age) 호출
     *                 -> GET -> :8080/target_server/get (FeignTargetController) 요청
     *                 -> 응답 : BaseResponseInfo.builder().header(header).name(name).age(age).build();
     *             -> 응답 : feignDemoClient.get()
     *         -> 응답 : feignDemoService.get() 안에 System.out()
     */
    @GetMapping("/get")
    public String getController() {
        return feignDemoService.get();
    }

    @PostMapping("/post")
    public String postControllerWithGet() {
        return feignDemoService.post();
    }

    @GetMapping("/error")
    public String eoorDecoderContorller() {
        return feignDemoService.errorDecoder();
    }
}
