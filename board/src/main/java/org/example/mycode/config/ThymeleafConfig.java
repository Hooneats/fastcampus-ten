package org.example.mycode.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

/**
 * decoupled 기능 ~th.xml
 * -> 서버가 없이(작동 안할 때) 순수 html 작동하도록 디자인 따로 관리
 */
//@Configuration
public class ThymeleafConfig {

//    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());

        return defaultTemplateResolver;
    }


    @RequiredArgsConstructor
    @Getter
    @ConstructorBinding
//    @ConfigurationProperties("spring.thymeleaf3") // ConfigurationProperties 를 이런식으로 직접 만들어 사용할경우 RootClass 에 @ConfigurationPropertiesScan 추가
    public static class Thymeleaf3Properties {
        /**
         * Use Thymeleaf 3 Decoupled Logic
         */
        private final boolean decoupledLogic;
    }
}
