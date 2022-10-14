package org.example.mvc;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class HandlerKey {

    private final RequestMethod requestMethod;
    private final String uriPath;

    public HandlerKey(RequestMethod requestMethod, String uriPath) {
        this.requestMethod = requestMethod;
        this.uriPath = uriPath;
    }
}
