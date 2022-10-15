package org.example.di;

import java.lang.reflect.Constructor;
import java.util.Set;
import org.example.annotation.Inject;
import org.reflections.ReflectionUtils;

public class BeanFactoryUtils {

    public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
        // @Inject 어노테이션이 붙은 Constructor 만 가져오기
        Set<Constructor> injectedConstructors = ReflectionUtils.getAllConstructors(clazz,
            ReflectionUtils.withAnnotation(Inject.class));
        if (injectedConstructors.isEmpty()) {
            return null;
        }
        // 있으면 생성자 하나 리턴
        return injectedConstructors.iterator().next();
    }

}
