package org.example.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnotationHandler {


    private final Class<?> clazz;
    private final Method targetMethod;

    public AnnotationHandler(Class<?> clazz, Method declaredMethod) {
        this.clazz = clazz;
        this.targetMethod = declaredMethod;
    }

    public String handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Constructor<?> noArgConstructor = clazz.getDeclaredConstructor();
        Object handler = noArgConstructor.newInstance();

        // 메소드 호출
        return (String) targetMethod.invoke(handler, request, response);
    }
}
