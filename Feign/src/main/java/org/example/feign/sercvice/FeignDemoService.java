package org.example.feign.sercvice;

import lombok.RequiredArgsConstructor;
import org.example.feign.fclient.client.FeignDemoClient;
import org.example.feign.fclient.common.dto.BaseRequestInfo;
import org.example.feign.fclient.common.dto.BaseResponseInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeignDemoService {

    private final FeignDemoClient feignDemoClient;

    public String get() {
        System.out.println("feignDemoClient.get() 호출 전 : " + Thread.currentThread().getName());
        ResponseEntity<BaseResponseInfo> response =
                feignDemoClient.callGet(
                        "CustomHeader", "CustomName", 1L
                );
        System.out.println("feignDemoClient.get() 호출 후 : " + Thread.currentThread().getName());
        System.out.println("Name : " + response.getBody().getName());
        System.out.println("Age : " + response.getBody().getAge());
        System.out.println("Header : " + response.getBody().getHeader());
        return "get";
    }

    public String post() {
        System.out.println("feignDemoClient.post() 호출 전 : " + Thread.currentThread().getName());
        BaseRequestInfo baseRequestInfo = BaseRequestInfo.builder()
                .name("customName")
                .age(2L)
                .build();
        ResponseEntity<BaseResponseInfo> response =
                feignDemoClient.callPost(
                        "CustomHeader", baseRequestInfo
                );
        System.out.println("feignDemoClient.post() 호출 후 : " + Thread.currentThread().getName());
        System.out.println("Name : " + response.getBody().getName());
        System.out.println("Age : " + response.getBody().getAge());
        System.out.println("Header : " + response.getBody().getHeader());
        return "post";
    }

    public String errorDecoder() {

        ResponseEntity<BaseResponseInfo> response =
                feignDemoClient.callErrorDecoder();
        return "error";
    }

}
