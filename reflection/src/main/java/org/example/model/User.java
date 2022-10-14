package org.example.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    private String userId;
    private String name;

    public boolean equalsUser(User user) {
        return this.equals(user);
    }
}
