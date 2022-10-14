package org.example.mvc;

import java.util.HashMap;
import java.util.Map;
import org.example.mvc.controller.Controller;
import org.example.mvc.controller.HomeController;

public class RequestMappingHandlerMapping {

    // key: urlPath, value: Controller
    private Map<String, Controller> mappings = new HashMap<>();

    void init() {
        mappings.put("/", new HomeController());
    }

    public Controller findHandler(String uriPath) {
        return mappings.get(uriPath);
    }
}
