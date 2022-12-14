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
import org.example.mvc.handler.adapter.AnnotationHandlerAdapter;
import org.example.mvc.handler.adapter.HandlerAdapter;
import org.example.mvc.handler.adapter.SimpleControllerHandlerAdapter;
import org.example.mvc.handler.mapping.AnnotationHandlerMapping;
import org.example.mvc.handler.mapping.vo.HandlerKey;
import org.example.mvc.handler.mapping.HandlerMapping;
import org.example.mvc.handler.mapping.RequestMappingHandlerMapping;
import org.example.mvc.handler.mapping.vo.RequestMethod;
import org.example.mvc.view.resolver.JspViewResolver;
import org.example.mvc.view.vo.ModelAndView;
import org.example.mvc.view.View;
import org.example.mvc.view.resolver.ViewResolver;

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
            // handler mappings ?????? handler(Controller)??? ??????
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

            // HandlerAdapter ?????? ????????? Handler(Controller) ?????? ????????? ??????????????? ??????.(????????? ???????????? ????????? ??????)
            // handler(Controller) ?????? ????????? ????????????. -> controller ??? viewName ??? ????????????.
            HandlerAdapter filteredHandlerAdapter = handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findFirst()
                .orElseThrow(
                    () -> new ServletException("No adapter for handelr [" + handler + "]"));
            ModelAndView modelAndView = filteredHandlerAdapter.handle(request, response, handler);

            // viewName ??? redirect:/users ?????? redirect: ?????? AND forward ?????????
            // ??? ViewResolver ??? ???????????????
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
