package org.example.mvc.handler.mapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.example.mvc.handler.AnnotationHandler;
import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;
import org.example.mvc.handler.mapping.vo.HandlerKey;
import org.example.mvc.handler.mapping.vo.RequestMethod;
import org.reflections.Reflections;

public class AnnotationHandlerMapping implements HandlerMapping {

    private final Object[] basePackage;
    private Map<HandlerKey, AnnotationHandler> handlers = new HashMap<>();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialIze() {
        Reflections reflections = new Reflections(basePackage);

        // org.example.mvc.annotation.Controller
        Set<Class<?>> annotatedWithController = reflections.getTypesAnnotatedWith(Controller.class);

        annotatedWithController.forEach(clazz -> {
            Arrays.stream(clazz.getDeclaredMethods())
                .forEach(declaredMethod -> {
                    RequestMapping requestMapping = declaredMethod.getDeclaredAnnotation(
                        RequestMapping.class);

                    Arrays.stream(getRequestMethods(requestMapping))
                        .forEach(requestMethod -> {
                            handlers.put(
                                new HandlerKey(requestMethod, requestMapping.value()),
                                new AnnotationHandler(clazz, declaredMethod)
                            );
                        });

                });
        });
    }

    private RequestMethod[] getRequestMethods(RequestMapping requestMapping) {
        return requestMapping.method();
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return handlers.get(handlerKey);
    }
}
