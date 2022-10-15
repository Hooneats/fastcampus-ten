package org.example.mvc.view.resolver;

import org.example.mvc.view.View;

public interface ViewResolver {
    View resolveView(String viewName);
}
