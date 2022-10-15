package org.example.mvc;

import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;
import org.example.mvc.controller.*;

public class RequestMappingHandlerMapping implements HandlerMapping{

    // key: urlPath, value: Controller
    private Map<HandlerKey, Controller> mappings = new HashMap<>();

    public void init() {
//        mappings.put(new HandlerKey(RequestMethod.GET,"/"), new HomeController()); // Annotation 기반으로 변경
        mappings.put(new HandlerKey(RequestMethod.GET,"/users"), new UserListController());
        mappings.put(new HandlerKey(RequestMethod.POST,"/users"), new UserCreateController());
        mappings.put(new HandlerKey(RequestMethod.GET,"/user/form"), new ForwardController("/user/form"));
    }

    public Object findHandler(HandlerKey handlerKey) {
        return mappings.get(handlerKey);
    }
}
