package org.example.mvc.handler.mapping;

import org.example.mvc.handler.mapping.vo.HandlerKey;

public interface HandlerMapping {

    Object findHandler(HandlerKey handlerKey);
}
