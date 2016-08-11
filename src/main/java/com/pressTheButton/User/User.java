package com.pressTheButton.User;

/**
 * Created by Tyler on 2016-08-10.
 */
public class User {
    private final long id;
    private final String name;

    public User(long id,
                String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName(){
        return name;
    }
}
