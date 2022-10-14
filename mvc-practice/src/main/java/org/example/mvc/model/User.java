package org.example.mvc.model;

import lombok.Getter;

@Getter
public class User {
    private final String userId;
    private final String name;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
