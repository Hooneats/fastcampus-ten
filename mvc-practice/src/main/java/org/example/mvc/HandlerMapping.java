package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.mvc.controller.ForwardController;
import org.example.mvc.controller.HomeController;
import org.example.mvc.controller.UserCreateController;
import org.example.mvc.controller.UserListController;

public interface HandlerMapping {

    Object findHandler(HandlerKey handlerKey);
}
