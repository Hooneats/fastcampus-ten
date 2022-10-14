package org.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.annotation.Controller;
import org.example.annotation.RequestMapping;
import org.example.annotation.RequestMethod;

@Controller
public class HealthCheckController {

    @RequestMapping(value = "/health", methods = RequestMethod.GET)
    public String health(HttpServletRequest request, HttpServletResponse response) {
        return "ok";
    }

}
