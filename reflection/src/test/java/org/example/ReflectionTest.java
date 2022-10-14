package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.annotation.Controller;
import org.example.annotation.Service;
import org.example.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Controller 에노테이션이 설정된 모든 클래스를 찾는다.
 */
public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);


    @Test
    void controllerScan() {
        Set<Class<?>> beans = getTypesAnnotatedWith(List.of(Controller.class, Service.class));

        logger.debug("beans [{}]", beans);
    }

    @Test
    void showClass() {
        Class<User> userClass = User.class;
        logger.debug("class getName {}", userClass.getName());

        logger.debug("User all declared fields {}",
            Arrays.stream(userClass.getDeclaredFields()).collect(Collectors.toList()));

        logger.debug("User all declared constructors {}",
            Arrays.stream(userClass.getDeclaredConstructors()).collect(Collectors.toList()));

        logger.debug("User all declared methods {}",
            Arrays.stream(userClass.getDeclaredMethods()).collect(Collectors.toList()));

    }

    @DisplayName("힙 영역에 로드되어 있는 클래스 타입의 객체 가져오기")
    @Test
    void load() throws ClassNotFoundException {
        //1.
        Class<User> userClass1 = User.class;

        //2.
        User user = new User("id", "name");
        Class<? extends User> userClass2 = user.getClass();

        //3.
        Class<?> userClass3 = Class.forName("org.example.model.User");

        logger.debug("class1 : [{}]", userClass1);
        logger.debug("class2 : [{}]", userClass2);
        logger.debug("class3 : [{}]", userClass3);

        assertThat(userClass1).isEqualTo(userClass2).isEqualTo(userClass3);
        assertThat(userClass1 == userClass2 && userClass2 == userClass3).isTrue();
    }

    private static Set<Class<?>> getTypesAnnotatedWith(List<Class<? extends Annotation>> annotations) {
        Reflections reflections = new Reflections("org.example");

        Set<Class<?>> beans = new HashSet<>();

        annotations.forEach(
            annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));
        return beans;
    }
}
