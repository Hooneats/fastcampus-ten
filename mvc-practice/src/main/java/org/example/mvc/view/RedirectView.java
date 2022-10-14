package org.example.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RedirectView implements View {
    public static final String DEFAULT_REDIRCT_PRIFIX = "redirect:";
    // viewName
    private final String name;

    public RedirectView(String name) {
        this.name = name;
    }

    /**
     * forward 방식
     */
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendRedirect(name.substring(DEFAULT_REDIRCT_PRIFIX.length()));
    }
}
