package com.pressTheButton.User;

import org.springframework.stereotype.Component;

/**
 * Created by Tyler on 2016-08-10.
 */
@Component
public class User {
    private final String id;
    private final String name;

    public User(String id,
                String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }
}
