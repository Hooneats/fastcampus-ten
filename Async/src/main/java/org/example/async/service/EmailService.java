package org.example.async.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Async("defaultTaskExecutor")
    public void sendMail() {
        System.out.println("{defaultTaskExecutor} :: " + Thread.currentThread().getName());
    }

    @Async("messageTaskExecutor")
    public void sendMailWithCustomThreadPool() {
        System.out.println("{messagingTaskExecutor} :: " + Thread.currentThread().getName());
    }
}
