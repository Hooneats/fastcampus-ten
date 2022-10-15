package org.example.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ControllerInterface {

    String handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
