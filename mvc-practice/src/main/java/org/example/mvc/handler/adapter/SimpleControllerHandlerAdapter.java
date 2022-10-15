package org.example.mvc.handler.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.mvc.controller.ControllerInterface;
import org.example.mvc.view.vo.ModelAndView;

public class SimpleControllerHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerInterface);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        String viewName = ((ControllerInterface) handler).handleRequest(request, response);
        return new ModelAndView(viewName);
    }
}
