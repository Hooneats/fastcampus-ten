package org.example.mvc;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.mvc.view.JspViewResolver;
import org.example.mvc.view.ModelAndView;
import org.example.mvc.view.View;
import org.example.mvc.view.ViewResolver;

@Slf4j
@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private List<HandlerMapping> handlerMappings;

    private List<HandlerAdapter> handlerAdapters;

    private List<ViewResolver> viewResolvers;

    @Override
    public void init() throws ServletException {
        RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.init();

        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("org.example");
        annotationHandlerMapping.initialIze();

        handlerMappings = List.of(requestMappingHandlerMapping, annotationHandlerMapping);

        handlerAdapters = List.of(new SimpleControllerHandlerAdapter(), new AnnotationHandlerAdapter());

        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        log.info("DispatcherServlet.service");

        String requestUri = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());

        try {
            // handler mappings 에서 handler(Controller)를 찾고
            Object handler =
                handlerMappings.stream()
                    .filter(handlerMapping ->
                        handlerMapping.findHandler(new HandlerKey(requestMethod, requestUri)) != null
                    )
                    .map(handlerMapping -> handlerMapping.findHandler(
                        new HandlerKey(requestMethod, requestUri)))
                    .findFirst()
                    .orElseThrow(() -> new ServletException(
                        "No Handler for Request Method:" + requestMethod + ", Reuqest Uri:"
                            + requestUri));

            // HandlerAdapter 에게 위임해 Handler(Controller) 인지 확인후 실행하도록 한다.(어뎁터 내부에서 핸들러 실행)
            // handler(Controller) 에게 작업을 위임한다. -> controller 는 viewName 을 반환한다.
            HandlerAdapter filteredHandlerAdapter = handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findFirst()
                .orElseThrow(
                    () -> new ServletException("No adapter for handelr [" + handler + "]"));
            ModelAndView modelAndView = filteredHandlerAdapter.handle(request, response, handler);

            // viewName 이 redirect:/users 이면 redirect: 제외 AND forward 기능도
            // ㄴ ViewResolver 로 처리해보자
            viewResolvers.forEach(viewResolver -> {
                View view = viewResolver.resolveView(modelAndView.getViewName());
                try {
                    view.render(modelAndView.getModel(), request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            log.error("exception occurred [{}]", e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
