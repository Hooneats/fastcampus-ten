package org.example.feign.fclient.config;

import org.example.feign.fclient.decoder.FeignDemoErrorDecoder;
import org.example.feign.fclient.interceptor.FeignDemoInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * client 를 위한 @Configuration
 */
@Configuration
public class FeignDemoConfig {

    @Bean
    public FeignDemoInterceptor feignDemoInterceptor() {
        return FeignDemoInterceptor.of();
    }

    @Bean
    public FeignDemoErrorDecoder feignDemoErrorDecoder() {
        return new FeignDemoErrorDecoder();
    }
}
