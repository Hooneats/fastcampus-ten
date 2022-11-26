package org.example.async.feign.config;

import org.example.async.feign.decoder.FeignDemoErrorDecoder;
import org.example.async.feign.interceptor.FeignDemoInterceptor;
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
