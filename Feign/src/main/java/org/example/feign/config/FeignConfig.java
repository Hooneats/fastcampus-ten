package org.example.feign.config;

import feign.Logger;
import org.example.feign.fclient.logger.FeignCustomLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Global 한 용도의 @Configuration
 */
@Configuration
public class FeignConfig {

    @Bean
    public Logger feignLogger() {
        return new FeignCustomLogger();
    }

}
