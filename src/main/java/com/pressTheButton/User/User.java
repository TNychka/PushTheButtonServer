package com.pressTheButton.User;

import org.springframework.stereotype.Component;

/**
 * Created by Tyler on 2016-08-10.
 */
@Component
public class User {
    private final String id;

    public User(String id,
                String name) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
