package org.example.async.controller;

import lombok.RequiredArgsConstructor;
import org.example.async.service.AsyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AsyncController {

    private final AsyncService asyncService;

    /**
     * @Async 를 사용하기 위해서는 스프링의 프록시 기술이 적용되게 코드를 작성해야한다.
     * 1번의 경우 스프링이 빈생성시 @Async 를 확인해 프록시로 빈을 생성한다.
     * 2번의 경우 직접 new 키워드로 객체를 만들어 사용했기에 프록시 기술이 적용되지 않는다.
     * 3번의 경우 internal 메서드 호출의 경우 this.메서드 를 사용하는 것이기에
     *          프록시가 실행하는것이 아닌 현재 (구현된)객체가 실행하는 것이어서 적용되지 않는다.
     *
     * ===> 때문에 @Async 를 다룰때는 쓰레드 이름을 찍어 꼭 확인하는 습관을 갖는게 좋고,
     *           @Async 를 메서드는 반드시 public 으로 사용해야 프록시로 실행을 할 수 있어 비동기가 적용된다.
     */
    @GetMapping("/1")
    public String asyncCall_1() {
        System.out.println("----------------------------------");
        asyncService.asyncCall_1(); // 빈주입 -> 결과 : 비동기 o
        return "success";
    }

    @GetMapping("/2")
    public String asyncCall_2() {
        System.out.println("----------------------------------");
        asyncService.asyncCall_2(); // 지역 변수 -> 결과 : 비동기 x
        return "success";
    }

    @GetMapping("/3")
    public String asyncCall_3() {
        System.out.println("----------------------------------");
        asyncService.asyncCall_3(); // 내부 메소드 -> 결과 : 비동기 x
        return "success";
    }
}
