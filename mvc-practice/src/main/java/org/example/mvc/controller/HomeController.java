package org.example.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.mvc.RequestMappingHandlerMapping;

public class HomeController implements Controller{

    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        return "home";
    }
}
