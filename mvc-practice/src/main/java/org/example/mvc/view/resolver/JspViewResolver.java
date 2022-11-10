package org.example.mvc.view.resolver;

import org.example.mvc.view.JspView;
import org.example.mvc.view.RedirectView;
import org.example.mvc.view.View;
import org.example.mvc.view.resolver.ViewResolver;

public class JspViewResolver implements ViewResolver {
    @Override
    public View resolveView(String viewName) {
        if (viewName.startsWith(RedirectView.DEFAULT_REDIRCT_PRIFIX)) {
            return new RedirectView(viewName);
        }
        return new JspView(viewName + ".jsp");
    }
}
