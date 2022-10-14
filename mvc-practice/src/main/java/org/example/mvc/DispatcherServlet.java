package org.example.mvc;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.mvc.controller.Controller;
import org.example.mvc.view.JspViewResolver;
import org.example.mvc.view.View;
import org.example.mvc.view.ViewResolver;

@Slf4j
@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private List<ViewResolver> viewResolvers;

    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void init() throws ServletException {
        this.requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.init();

        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        log.info("DispatcherServlet.service");
        try {
            // handler mappings 에서 handler(Controller)를 찾고
            Controller handler =
                requestMappingHandlerMapping
                    .findHandler(new HandlerKey(RequestMethod.valueOf(request.getMethod()), request.getRequestURI()));

            // handler(Controller) 에게 작업을 위임한다. -> controller 는 viewName 을 반환한다.
            String viewName = handler.handleRequest(request, response);

            // viewName 이 redirect:/users 이면 redirect: 제외 AND forward 기능도
            // ㄴ ViewResolver 로 처리해보자

            viewResolvers.forEach(viewResolver -> {
                View view = viewResolver.resolveView(viewName);
                try {
                    view.render(new HashMap<>(), request, response);
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
