package org.example.servlet;

import org.example.calculator.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "calculatorServlet", urlPatterns = "/calculate")
public class CalculatorServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(CalculatorServlet.class);

    @Override
    public String getServletName() {
        return "calculatorServlet";
    }

    @Override
    public void init() throws ServletException {
        log.info("init");
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        log.info("service");
        int operand1 = Integer.parseInt(request.getParameter("operand1"));
        String operator = request.getParameter("operator");
        int operand2 = Integer.parseInt(request.getParameter("operand2"));

        int result = Calculator.calculate(operand1, operator, operand2);

        PrintWriter writer = response.getWriter();
        writer.println(result);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("service");
        int operand1 = Integer.parseInt(request.getParameter("operand1"));
        String operator = request.getParameter("operator");
        int operand2 = Integer.parseInt(request.getParameter("operand2"));

        int result = Calculator.calculate(operand1, operator, operand2);

        PrintWriter writer = response.getWriter();
        writer.println(result);
    }

}