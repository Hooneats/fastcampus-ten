package org.example.async.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AppConfig {

    @Bean(name = "defaultTaskExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor defaultTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(200); // 최소 (기본) 쓰레드 갯수
        threadPoolTaskExecutor.setMaxPoolSize(300); // 늘어난다면 최대로 늘어날 쓰레드 갯수
//        threadPoolTaskExecutor.setKeepAliveSeconds(); // 기본이 60 초 ( 현재 생존한 쓰레드 갯구가 core < max 상태일때 core 로 다시 줄이기위한 활동하지 않는 쓰레드 허용시간 )
//        threadPoolTaskExecutor.setQueueCapacity(); // 기본이 Integer.MAX_VALUE (작업요청이 core 갯수를 넘었다고 쓰레드를 만드는게 아닌, 작업요청이 오면 큐에 쌓이고 큐의 정해진 싸이즈가 넘어서면 그때 max 갯수까지 쓰레드를 늘린다.)
        return threadPoolTaskExecutor;
    }

    @Bean(name = "messageTaskExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor messageTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(200);
        threadPoolTaskExecutor.setMaxPoolSize(300) ;
        return threadPoolTaskExecutor;
    }
}
