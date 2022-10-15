package org.example.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.example.annotation.Inject;
import org.example.controller.UserController;
import org.reflections.ReflectionUtils;

public class BeanFactory {

    private final Set<Class<?>> preInstantialtedClazz;
    private Map<Class<?>, Object> beans = new HashMap<>();

    public BeanFactory(Set<Class<?>> preInstantialtedClazz) {
        this.preInstantialtedClazz = preInstantialtedClazz;
        initialize();
    }

    private void initialize() {
        preInstantialtedClazz.stream()
            .forEach(clazz-> {
                Object instance = createInstance(clazz);
                beans.put(clazz, instance);
            });
    }

    // ex1) UserController
    private Object createInstance(Class<?> clazz) {
        // ex1) UserController @Inject 붙은 생성자
        // 생성자
        Constructor<?> constructor = findConstructor(clazz);

        // ex1) UserController @Inject 붙은 생성자의 파라미터인 UserService 의 인스턴스 찾기 및 없으면 생성
        // 파라미터 정보
        List<Object> parameters = new ArrayList<>();
        Arrays.stream(constructor.getParameterTypes())
            .forEach(parameterTypeClass -> parameters.add(getParameterByClass(parameterTypeClass)));

        // ex1) 위에서 찾은 파라미터의 인스턴스를 가지고 UserController 생성자에 넣어 UserController 인스턴스 생성
        // 최종 인스턴스 생성
        try {
            return constructor.newInstance(parameters.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Constructor<?> findConstructor(Class<?> clazz) {
        // @Inject 어노테이션이 붙은 Constructor 만 가져오기
        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz);

        if (Objects.nonNull(constructor)) {
            return constructor;
        }

        // @Inject 붙은 constructor 가 없으면 첫번째 constructor 반환
        return clazz.getConstructors()[0];
    }

    private Object getParameterByClass(Class<?> parameterTypeClass) {
        // instance 가 이미 만들어졌는지 확인
        Object instanceBean = getBean(parameterTypeClass);
        if (Objects.nonNull(instanceBean)) {
            return instanceBean;
        }

        // 안만들어졌다면 다시 만들어 반환
        return createInstance(parameterTypeClass);
    }

    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }
}
