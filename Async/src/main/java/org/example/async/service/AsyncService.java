package org.example.async.service;

import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncService {

    private final EmailService emailService;

    public void asyncCall_1() {
        System.out.println("{asyncCall_1} :: " + Thread.currentThread().getName());
        System.out.println("AopProxy is " + AopUtils.isAopProxy(emailService));
        System.out.println(emailService.getClass());
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
     }

    public void asyncCall_2() {
        System.out.println("{asyncCall_2} :: " + Thread.currentThread().getName());
        EmailService _emailService = new EmailService();
        System.out.println("AopProxy is " + AopUtils.isAopProxy(_emailService));
        System.out.println(_emailService.getClass());
        _emailService.sendMail();
        _emailService.sendMailWithCustomThreadPool();
    }

    public void asyncCall_3() {
        System.out.println("{asyncCall_3} :: " + Thread.currentThread().getName());
        System.out.println("AopProxy is " + AopUtils.isAopProxy(this));
        System.out.println(this.getClass());
        sendMail();
    }

    @Async
    public void sendMail() {
        System.out.println("{internal_@Async_sendMail} :: " + Thread.currentThread().getName());
    }
}
