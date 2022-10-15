package org.example.mvc.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class ModelAndView {

    private Object view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView(String viewName) {
        this.view = viewName;
    }

    public Map<String, Object> getModel() {
        // 불변으로 리턴
        return Collections.unmodifiableMap(model);
    }

    public String getViewName() {
        return (this.view instanceof String) ? this.view.toString() : null;
    }
}

