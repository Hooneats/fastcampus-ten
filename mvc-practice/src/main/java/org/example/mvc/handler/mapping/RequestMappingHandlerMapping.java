package org.example.mvc.handler.mapping;

import java.util.HashMap;
import java.util.Map;
import org.example.mvc.controller.*;
import org.example.mvc.handler.mapping.vo.HandlerKey;
import org.example.mvc.handler.mapping.vo.RequestMethod;

public class RequestMappingHandlerMapping implements HandlerMapping {

    // key: urlPath, value: Controller
    private Map<HandlerKey, ControllerInterface> mappings = new HashMap<>();

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
