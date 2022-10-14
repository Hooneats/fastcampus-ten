package org.example.mvc.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View {
    // viewName
    private final String name;

    public JspView(String name) {
        this.name = name;
    }

    /**
     * forward 방식
     */
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // reqeust에 map 을 그대로 넣음
        model.forEach(request::setAttribute);
        // viewName 으로 RequestDispatcher 생성해 위임한다.
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(name);
        requestDispatcher.forward(request, response);
    }
}
