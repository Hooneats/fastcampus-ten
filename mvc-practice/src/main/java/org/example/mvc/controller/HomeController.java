package org.example.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.mvc.handler.mapping.RequestMappingHandlerMapping;
import org.example.mvc.handler.mapping.vo.RequestMethod;
import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;

@Controller
public class HomeController {

    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public String handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        return "home";
    }
}
